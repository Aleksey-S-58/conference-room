package com.gmail.ascanada52.conference.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.dto.User;
import com.gmail.ascanada52.conference.room.repositories.UserRepository;
import com.gmail.ascanada52.conference.room.security.AuthenticationService;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private AuthenticationService authenticationService; // TODO may be we should pull in this logic to avoid unnecessary requests to a database. 
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public UserService(AuthenticationService authenticationService, UserRepository repository,
            PasswordEncoder passwordEncoder) {
        super();
        this.authenticationService = authenticationService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getInfo() {
        Optional<com.gmail.ascanada52.conference.room.entities.User> user = authenticationService.getAuthenticatedUser();
        return map(user);
    }

    @Transactional
    public User update(User updatedUser) {
        Optional<com.gmail.ascanada52.conference.room.entities.User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return null;
        }
        Optional<com.gmail.ascanada52.conference.room.entities.User> entity = repository.findById(user.get().getId());
        entity.ifPresent(e -> {
            e.setName(updatedUser.getName());
            e.setLogin(updatedUser.getLogin());
        });
        logger.info("Updated user id: {} login: {} new name: {} new login: {}", 
                user.get().getId(), user.get().getLogin(), updatedUser.getName(), updatedUser.getLogin());
        return map(entity);
    }

    @Transactional
    public boolean changePassword(User updatedUser) {
        Optional<com.gmail.ascanada52.conference.room.entities.User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return false;
        }
        Optional<com.gmail.ascanada52.conference.room.entities.User> entity = repository.findById(user.get().getId());
        if (!entity.isPresent()) {
            return false;
        }
        entity.ifPresent(e -> {
            e.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        });
        logger.info("User id: {} login: {} changed password", 
                user.get().getId(), user.get().getLogin());
        return true;
    }

    @Transactional
    public void delete(User user) {
        logger.info("user deleted by login: {}", user.getLogin());
        repository.deleteByLogin(user.getLogin());
    }

    @Transactional
    public void delete() {
        Optional<com.gmail.ascanada52.conference.room.entities.User> user = authenticationService.getAuthenticatedUser();
        logger.info("user with login: {} removed account", user.get().getLogin());
        user.ifPresent(u -> repository.deleteByLogin(u.getLogin()));
    }

    public List<User> findByIdList(List<Integer> ids) {
        return repository.findByIdIn(ids).stream()
                .map(u -> map(u))
                .collect(Collectors.toList());
    }

    public Optional<User> findById(Integer id) {
        return repository.findById(id)
                .map(u -> map(u));
    }

    public List<User> findByNameAndLogin(String name, String login) {
        if (StringUtils.isAllBlank(name, login)) {
            return new ArrayList<>();
        }
        if (StringUtils.isBlank(login)) {
            return repository.findByNameLike(name).stream()
                    .map(u -> map(u))
                    .collect(Collectors.toList());
        }
        if (StringUtils.isBlank(name)) {
            return repository.findByLoginLike(name).stream()
                    .map(u -> map(u))
                    .collect(Collectors.toList());
        }
        return repository.findByNameLikeAndLoginLike(name, login).stream()
                .map(u -> map(u))
                .collect(Collectors.toList());
    }

    private User map(Optional<com.gmail.ascanada52.conference.room.entities.User> user) {
        return user
                .map(u -> map(u))
                .get();
    }

    private User map(com.gmail.ascanada52.conference.room.entities.User u) {
        return new User(u.getId(), u.getName(), u.getLogin(), "", u.getRole());
    }
}
