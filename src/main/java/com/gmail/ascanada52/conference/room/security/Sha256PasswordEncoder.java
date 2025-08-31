package com.gmail.ascanada52.conference.room.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Sha256PasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger("application-test");
    private Pbkdf2PasswordEncoder passwordEncoder;

    public Sha256PasswordEncoder(@Value("conference.room.password") String password) {
        passwordEncoder = new Pbkdf2PasswordEncoder(password);
        passwordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        passwordEncoder.setEncodeHashAsBase64(true);
        // TODO Remove message about default login/password
        logger.debug("default login/password is: admin  {}", passwordEncoder.encode("admin"));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
