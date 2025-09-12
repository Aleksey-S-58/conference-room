package com.gmail.ascanada52.conference.room.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gmail.ascanada52.conference.room.dto.User;
import com.gmail.ascanada52.conference.room.service.ContactsService;
import com.gmail.ascanada52.conference.room.service.UserService;

@RequestMapping(path = "/contacts")
@Controller
public class ContactsController {

    private ContactsService contactsService;
    private UserService userService;

    public ContactsController(ContactsService contactsService, UserService userService) {
        super();
        this.contactsService = contactsService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getContacts() {
        return ResponseEntity.ok(contactsService.getAll());
    }

    @GetMapping("/find")
    public ResponseEntity<List<User>> find(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "login", required = false) String login) {
        return ResponseEntity.ok(userService.findByNameAndLogin(name, login));
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<User> add(@PathVariable("id") Integer id) {
        Optional<User> newContact = contactsService.addUser(id);
        if (newContact.isPresent()) {
            return ResponseEntity.ok(newContact.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Integer id) {
        contactsService.removeContact(id);
        return ResponseEntity.ok().build();
    }
}
