package com.gmail.ascanada52.conference.room.security;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.repositories.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is blank!");
        }
        List<User> users = repository.findByLogin(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Login wasn't found in the repository!");
        }
        User user = users.get(0);
        return new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole().name())));
    }

}
