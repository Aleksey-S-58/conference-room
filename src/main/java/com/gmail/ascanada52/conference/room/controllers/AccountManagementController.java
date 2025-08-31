package com.gmail.ascanada52.conference.room.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gmail.ascanada52.conference.room.dto.User;
import com.gmail.ascanada52.conference.room.service.AccountManagementService;

@Controller
@RequestMapping("/accounts")
public class AccountManagementController {

    private AccountManagementService service;

    public AccountManagementController(AccountManagementService service) {
        super();
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<User>> getPage(@RequestParam(name = "pageNumber", required = false) Integer pageNumber, 
            @RequestParam(name = "size", required = false) Integer size) {
        return ResponseEntity.ok(service.getPage(pageNumber, size));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(service.create(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @RequestBody User user) {
        Optional<User> updated = service.updateUser(id, user);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
