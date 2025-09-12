package com.gmail.ascanada52.conference.room.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.dto.AttachmentDto;
import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.entities.chat.Attachment;
import com.gmail.ascanada52.conference.room.entities.chat.Chat;
import com.gmail.ascanada52.conference.room.entities.chat.Message;
import com.gmail.ascanada52.conference.room.entities.chat.Participant;
import com.gmail.ascanada52.conference.room.repositories.AttachmentRepository;
import com.gmail.ascanada52.conference.room.repositories.ChatRepository;
import com.gmail.ascanada52.conference.room.repositories.MessageRepository;
import com.gmail.ascanada52.conference.room.security.AuthenticationService;

@Service
public class AttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);
    private AttachmentRepository attachmentRepository;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    private AuthenticationService authenticationService;

    public AttachmentService(AttachmentRepository attachmentRepository, ChatRepository chatRepository,
            MessageRepository messageRepository, AuthenticationService authenticationService) {
        super();
        this.attachmentRepository = attachmentRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public Optional<Attachment> getAttachment(Long messageId, Long attachmentId) {
        // Check that user exists and present in chat participants
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return Optional.empty();
        }
        Optional<Message> message = messageRepository.findById(messageId);
        if (!message.isPresent()) {
            return Optional.empty();
        }
        if (!Objects.equals(message.get().getAttachmentId(), attachmentId)) {
            return Optional.empty();
        }
        Optional<Chat> chat = chatRepository.findById(message.get().getChatId());
        if (!chat.isPresent()) {
            return Optional.empty();
        }
        List<Integer> participants = chat.get().getParticipants().stream()
                .map(Participant::getUserId)
                .collect(Collectors.toList());
        if (!participants.contains(user.get().getId())) {
            return Optional.empty();
        }
        return attachmentRepository.findById(attachmentId);
    }

    public Optional<Long> save(AttachmentDto dto) {
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return Optional.empty();
        }
        if (dto == null || StringUtils.isAnyBlank(dto.getMediaType(), dto.getFileName())
                || dto.getBytes() == null || dto.getBytes().length == 0) {
            return Optional.empty();
        }
        Attachment attachment = new Attachment(dto.getMediaType(), dto.getFileName(), dto.getBytes());
        Attachment entity = attachmentRepository.save(attachment);
        logger.info("user id: {}, login: {} saved attachment id: {}", user.get().getId(), user.get().getLogin(), entity.getId());
        return Optional.of(entity.getId());
    }
}
