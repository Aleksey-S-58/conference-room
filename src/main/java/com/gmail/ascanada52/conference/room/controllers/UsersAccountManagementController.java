package com.gmail.ascanada52.conference.room.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.ascanada52.conference.room.dto.User;
import com.gmail.ascanada52.conference.room.service.UserService;

@Controller
@RequestMapping(path = "/user")
public class UsersAccountManagementController {
    private UserService service;

    public UsersAccountManagementController(UserService service) {
        super();
        this.service = service;
    }

    @GetMapping("/info")
    public ResponseEntity<User> getInfo() {
        User user = service.getInfo();
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/change/password")
    public ResponseEntity<Void> changePassword(@RequestBody User user) {
        if (service.changePassword(user)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        User updatedUser = service.update(user);
        if (user != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        service.delete();
        return ResponseEntity.ok().build();
    }
}
