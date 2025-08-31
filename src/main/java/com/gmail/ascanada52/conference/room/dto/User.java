package com.gmail.ascanada52.conference.room.dto;

import com.gmail.ascanada52.conference.room.entities.Role;

public class User {
    private Integer id;
    private String name;
    private String login;
    private String password;
    private Role role;

    public User() {
        
    }

    public User(Integer id, String name, String login, String password, Role role) {
        super();
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
