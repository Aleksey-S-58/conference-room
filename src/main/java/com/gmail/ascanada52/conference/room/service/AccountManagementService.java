package com.gmail.ascanada52.conference.room.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.repositories.UserRepository;

@Service
public class AccountManagementService {

    private static Logger logger = LoggerFactory.getLogger(AccountManagementService.class);
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public AccountManagementService(UserRepository repository, PasswordEncoder passwordEncoder) {
        super();
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<com.gmail.ascanada52.conference.room.dto.User> getAll() {
        return repository.findAll().stream()
                .map(u -> new com.gmail.ascanada52.conference.room.dto.User(
                u.getId(), u.getName(), u.getLogin(), "", u.getRole()))
                .collect(Collectors.toList());
    }

    public Page<com.gmail.ascanada52.conference.room.dto.User> getPage(Integer pageNumber, Integer size) {
        int number = pageNumber == null ? 0 : pageNumber;
        int pageSize = size == null ? 10 : size;
        Page<User> page = repository.findAll(PageRequest.of(number, pageSize));
        List<com.gmail.ascanada52.conference.room.dto.User> list = page.getContent().stream()
                .map(u -> new com.gmail.ascanada52.conference.room.dto.User(
                u.getId(), u.getName(), u.getLogin(), "", u.getRole()))
                .collect(Collectors.toList());
        return new PageImpl<com.gmail.ascanada52.conference.room.dto.User>(
                list, page.getPageable(), page.getTotalElements());
    }

    @Transactional
    public com.gmail.ascanada52.conference.room.dto.User create(com.gmail.ascanada52.conference.room.dto.User user) {
        User entity = new User(user.getLogin(), user.getName(), passwordEncoder.encode(user.getPassword()), user.getRole());
        User persistentUser = repository.save(entity);
        logger.info("created account with id: {}, login: {}, name: {}, role: {}", 
                persistentUser.getId(), persistentUser.getLogin(), persistentUser.getName(), persistentUser.getRole());
        return new com.gmail.ascanada52.conference.room.dto.User(persistentUser.getId(),
                persistentUser.getName(), persistentUser.getLogin(), "", persistentUser.getRole());
    }

    @Transactional
    public Optional<com.gmail.ascanada52.conference.room.dto.User> updateUser(Integer id, com.gmail.ascanada52.conference.room.dto.User user) {
        Optional<User> entity = repository.findById(id);
        if (!entity.isPresent()) {
            return Optional.empty();
        }
        entity.ifPresent(u -> {
            u.setName(user.getName());
            u.setLogin(user.getLogin());
            u.setRole(user.getRole());
            if (StringUtils.isNotBlank(user.getPassword())) {
                u.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        });
        logger.info("updated account with id: {} new values login: {}, name: {}, role: {}", 
                entity.get().getId(), entity.get().getLogin(), entity.get().getName(), entity.get().getRole());
        return entity.map(u -> new com.gmail.ascanada52.conference.room.dto.User(
                u.getId(), u.getName(), u.getLogin(), "", u.getRole()));
    }

    public boolean delete(Integer id) {
        logger.info("admin deleted account with id: {}", id);
        repository.deleteById(id);
        return true;
    }
}
