package com.gmail.ascanada52.conference.room.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.gmail.ascanada52.conference.room.dto.AttachmentDto;
import com.gmail.ascanada52.conference.room.entities.chat.Attachment;
import com.gmail.ascanada52.conference.room.service.AttachmentService;

@RequestMapping(path = "/attachment")
@Controller
public class AttachmentController {

    private AttachmentService service;

    public AttachmentController(AttachmentService service) {
        super();
        this.service = service;
    }

    @GetMapping("/{messageId}/{attachmentId}")
    public ResponseEntity<?> download(@PathVariable("messageId") Long messageId, 
            @PathVariable("attachmentId") Long attachmentId) throws UnsupportedEncodingException {
        Optional<Attachment> attachment = service.getAttachment(messageId, attachmentId);
        if (!attachment.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        MediaType type = MediaType.parseMediaType(attachment.get().getMediaType());
        return ResponseEntity.ok().contentLength(attachment.get().getBytes().length)
                .contentType(type)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(attachment.get().getFileName(), "UTF-8"))
                .body(attachment.get().getBytes());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Long> uplolad(@RequestPart MultipartFile file) throws IOException {
        AttachmentDto dto = new AttachmentDto(file.getName(), file.getContentType(), file.getBytes());
        Optional<Long> id = service.save(dto);
        if (id.isPresent()) {
            return ResponseEntity.ok(id.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
