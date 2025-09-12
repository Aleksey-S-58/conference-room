package com.gmail.ascanada52.conference.room.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.gmail.ascanada52.conference.room.entities.Role;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/actuator").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/accounts").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/user", "/message", "/attachment", "/invitation", "/contacts", "/conference", "/chat")
                    .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .antMatchers("/static/css/*.css").permitAll()
                .antMatchers("/static/css/*.map").permitAll()
                .antMatchers("/static/media/*.svg").permitAll()
                .antMatchers("/static/media/*.ttf").permitAll()
                .antMatchers("/static/media/*.eot").permitAll()
                .antMatchers("/static/media/*.woff").permitAll()
                .antMatchers("/static/media/*.woff2").permitAll()
                .antMatchers("/static/js/*.js").permitAll()
                .antMatchers("/static/js/*.map").permitAll()
                .antMatchers("/static/js/*.txt").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login-page.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login-error.html")
                .permitAll()
                .and()
            .logout().permitAll()
            .and().csrf().disable()
            .build();
    }
}
