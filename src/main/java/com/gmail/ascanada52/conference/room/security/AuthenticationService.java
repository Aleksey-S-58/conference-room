package com.gmail.ascanada52.conference.room.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.repositories.UserRepository;

@Service
public class AuthenticationService {
    private UserRepository repository;

    public AuthenticationService(UserRepository repository) {
        super();
        this.repository = repository;
    }

    public Optional<User> getAuthenticatedUser() {
        // TODO may be we should add here a map and don't involve database each time when we need user.
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = repository.findByLogin(user.getUsername());
        return users.isEmpty() ? Optional.empty() 
                : Optional.of(new User(users.get(0)));
    }
}
