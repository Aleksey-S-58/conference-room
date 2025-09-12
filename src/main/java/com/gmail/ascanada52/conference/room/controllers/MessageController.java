package com.gmail.ascanada52.conference.room.controllers;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.gmail.ascanada52.conference.room.dto.MessageWithAttachment;
import com.gmail.ascanada52.conference.room.service.MessageService;

@RequestMapping(path = "/message")
@Controller
public class MessageController {

    private MessageService service;

    public MessageController(MessageService service) {
        super();
        this.service = service;
    }

    @GetMapping("/{chatId}/latest/{offset}")
    public ResponseEntity<List<MessageWithAttachment>> getLatestMessagesForChat(
            @PathVariable("chatId") Integer chatId, 
            @PathVariable("offset") Long offset) {
        return ResponseEntity.ok(service.getLastMessagesForChat(chatId, offset));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Page<MessageWithAttachment>> getMessages(
            @PathVariable("chatId") Integer chatId,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(name = "offset", required = false, defaultValue = "9223372036854775807") Long offset) {
        return ResponseEntity.ok(service.getPage(chatId, pageNumber, pageSize, offset));
    }

    @PostMapping
    public ResponseEntity<MessageWithAttachment> sendMessage(@RequestBody MessageWithAttachment message) {
        Optional<MessageWithAttachment> saved = service.save(message);
        if (saved.isPresent()) {
            return ResponseEntity.ok(saved.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{chatId}/{messageId}")
    public ResponseEntity<MessageWithAttachment> changeMessage(
            @PathParam("chatId") Integer chatId,
            @PathParam("messageId") Long messageId,
            @RequestBody MessageWithAttachment message) {
        Optional<MessageWithAttachment> edited = service.edit(chatId, messageId, message);
        if (edited.isPresent()) {
            return ResponseEntity.ok(edited.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{chatId}/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathParam("chatId") Integer chatId, 
            @PathParam("messageId") Long messageId) {
        service.delete(chatId, messageId);
        return ResponseEntity.noContent().build();
    }
}
