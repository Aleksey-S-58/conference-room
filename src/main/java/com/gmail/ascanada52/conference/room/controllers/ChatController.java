package com.gmail.ascanada52.conference.room.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.ascanada52.conference.room.dto.ChatWithParticipants;
import com.gmail.ascanada52.conference.room.service.ChatService;

@RequestMapping(path = "/chat")
@Controller
public class ChatController {

    private ChatService service;

    public ChatController(ChatService service) {
        super();
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ChatWithParticipants>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<ChatWithParticipants> create(@RequestBody ChatWithParticipants chat) {
        Optional<ChatWithParticipants> createdChat = service.create(chat);
        if (createdChat.isPresent()) {
            return ResponseEntity.ok(createdChat.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ChatWithParticipants> update(@PathVariable("id") Integer id, @RequestBody ChatWithParticipants chat) {
        Optional<ChatWithParticipants> updatedChat = service.update(id, chat);
        if (updatedChat.isPresent()) {
            return ResponseEntity.ok(updatedChat.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
