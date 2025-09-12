package com.gmail.ascanada52.conference.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.dto.AttachmentDto;
import com.gmail.ascanada52.conference.room.dto.MessageWithAttachment;
import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.entities.chat.Attachment;
import com.gmail.ascanada52.conference.room.entities.chat.Message;
import com.gmail.ascanada52.conference.room.entities.chat.Participant;
import com.gmail.ascanada52.conference.room.entities.chat.ParticipantId;
import com.gmail.ascanada52.conference.room.repositories.AttachmentRepository;
import com.gmail.ascanada52.conference.room.repositories.MessageRepository;
import com.gmail.ascanada52.conference.room.repositories.ParticipantRepository;
import com.gmail.ascanada52.conference.room.security.AuthenticationService;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private MessageRepository messageRepository;
    private AttachmentRepository attachmentRepository;
    private ParticipantRepository participantRepository;
    private AuthenticationService authenticationService;
    private UserService userService;

    public MessageService(MessageRepository messageRepository, AttachmentRepository attachmentRepository,
            ParticipantRepository participantRepository, AuthenticationService authenticationService,
            UserService userService) {
        super();
        this.messageRepository = messageRepository;
        this.attachmentRepository = attachmentRepository;
        this.participantRepository = participantRepository;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public List<MessageWithAttachment> getLastMessagesForChat(Integer chatId, Long offset) {
        if (chatId == null || offset == null) {
            return new ArrayList<>();
        }
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return new ArrayList<>();
        }
        Optional<Participant> participant = participantRepository.findById(new ParticipantId(chatId, user.get().getId()));
        if (!participant.isPresent()) {
            return new ArrayList<>();
        }
        List<Message> messages = messageRepository.findByChatIdAndOffsetGreaterThanOrderByOffset(chatId, offset);
        return getMessagesWithAttachments(messages);
    }

    public Page<MessageWithAttachment> getPage(Integer chatId, Integer pageNumber, Integer pageSize, Long offset) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (chatId == null || offset == null) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        Optional<Participant> participant = participantRepository.findById(new ParticipantId(chatId, user.get().getId()));
        if (!participant.isPresent()) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        Page<Message> messages = messageRepository.findByChatIdAndOffsetLessThanOrderByOffsetDesc(chatId, offset, pageable);
        List<MessageWithAttachment> items = getMessagesWithAttachments(messages.getContent());
        return new PageImpl<>(items, messages.getPageable(), messages.getTotalElements());
    }

    @Transactional
    public Optional<MessageWithAttachment> save(MessageWithAttachment dto) {
        if (dto == null || dto.getChatId() == null) {
            return Optional.empty();
        }
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return Optional.empty();
        }
        Optional<Participant> participant = participantRepository.findById(new ParticipantId(dto.getChatId(), user.get().getId()));
        if (!participant.isPresent()) {
            return Optional.empty();
        }
        Optional<Attachment> attachment = dto.getAttachment() == null ? Optional.empty() : attachmentRepository.findAttachmentsInfoById(dto.getAttachment().getId());
        Long attachmentId = attachment.isPresent() ? dto.getAttachment().getId() : null;
        Message message = new Message(user.get().getId(), attachmentId, dto.getChatId(), dto.getText());
        Optional<Long> lastOffset = messageRepository.findMaxOffset(dto.getChatId());
        Long offset = lastOffset.isPresent() ? lastOffset.get() + 1 : 1;
        message.setOffset(offset);
        Message persistent = messageRepository.save(message);
        logger.info("user id: {}, login: {} saved message id: {}", user.get().getId(), user.get().getLogin(), persistent.getId());
        AttachmentDto attachmentInfo = attachment.isPresent() ? new AttachmentDto(attachment.get().getId(), attachment.get().getFileName(), attachment.get().getMediaType()) : null;
        return Optional.of(new MessageWithAttachment(persistent.getId(), persistent.getOffset(), 
                user.get().getId(), user.get().getName(), message.getChatId(), 
                attachmentInfo, message.getText(), message.getDate()));
    }

    @Transactional
    public Optional<MessageWithAttachment> edit(Integer chatId, Long messageId, MessageWithAttachment message) {
        if (chatId == null) {
            return Optional.empty();
        }
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return Optional.empty();
        }
        Optional<Participant> participant = participantRepository.findById(new ParticipantId(chatId, user.get().getId()));
        if (!participant.isPresent()) {
            return Optional.empty();
        }
        Optional<Message> m = messageRepository.findById(messageId);
        if (!m.isPresent() || Objects.equals(user.get().getId(), m.get().getUserId())) {
            return Optional.empty();
        }
        m.get().setText(message.getText());
        if (message.getAttachment() != null) {
            m.get().setAttachmentId(message.getAttachment().getId());
        } else {
            Long attachmentId = m.get().getAttachmentId();
            if (attachmentId != null) {
                if (!messageRepository.existsByAttachmentId(attachmentId)) {
                    logger.info("user id: {}, login: {} deleted attachment id: {}", user.get().getId(), user.get().getLogin(), attachmentId);
                    attachmentRepository.deleteById(attachmentId);
                }
            }
            m.get().setAttachmentId(null);
        }
        logger.info("user id: {}, login: {} edited message id: {}", user.get().getId(), user.get().getLogin(), messageId);
        Optional<Attachment> attachment = message.getAttachment() == null ? Optional.empty() : attachmentRepository.findAttachmentsInfoById(message.getAttachment().getId());
        AttachmentDto attachmentInfo = attachment.isPresent() ? new AttachmentDto(attachment.get().getId(), attachment.get().getFileName(), attachment.get().getMediaType()) : null;
        return Optional.of(new MessageWithAttachment(m.get().getId(), m.get().getOffset(), 
                user.get().getId(), user.get().getName(), m.get().getChatId(), 
                attachmentInfo, message.getText(), m.get().getDate()));
    }

    @Transactional
    public void delete(Integer chatId, Long messageId) {
        if (chatId == null) {
            return;
        }
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return;
        }
        Optional<Participant> participant = participantRepository.findById(new ParticipantId(chatId, user.get().getId()));
        if (!participant.isPresent()) {
            return;
        }
        Optional<Message> m = messageRepository.findById(messageId);
        if (!m.isPresent() || Objects.equals(user.get().getId(), m.get().getUserId())) {
            return;
        }
        Long attachmentId = m.isPresent() ? m.get().getAttachmentId() : null;
        logger.info("user id: {}, login: {} deleted message id: {}", user.get().getId(), user.get().getLogin(), messageId);
        messageRepository.deleteById(messageId);
        if (attachmentId != null) {
            if (!messageRepository.existsByAttachmentId(attachmentId)) {
                logger.info("user id: {}, login: {} deleted attachment id: {}", user.get().getId(), user.get().getLogin(), attachmentId);
                attachmentRepository.deleteById(attachmentId);
            }
        }
    }

    private List<MessageWithAttachment> getMessagesWithAttachments(List<Message> messages) {
        List<Long> attachmentIdList = messages.stream()
                .filter(m -> m.getAttachmentId() != null)
                .map(m -> m.getAttachmentId())
                .collect(Collectors.toList());
        List<Attachment> attachments = attachmentRepository.findAttachmentsInfoByIdList(attachmentIdList);
        List<Integer> usersIdList = messages.stream()
                .map(Message::getUserId)
                .collect(Collectors.toList());
        List<com.gmail.ascanada52.conference.room.dto.User> users = userService.findByIdList(usersIdList);
        return map(messages, attachments, users);
    }

    private List<MessageWithAttachment> map(List<Message> messages, List<Attachment> attachments, 
            List<com.gmail.ascanada52.conference.room.dto.User> users) {
        Map<Long, Attachment> attachmentsMap = attachments.stream()
                .collect(Collectors.toMap(Attachment::getId, a -> a));
        Map<Integer, String> usersMap = users.stream()
                .collect(Collectors.toMap(com.gmail.ascanada52.conference.room.dto.User::getId, com.gmail.ascanada52.conference.room.dto.User::getName));
        return messages.stream()
                .map(m -> {
                    Attachment a = attachmentsMap.get(m.getAttachmentId());
                    AttachmentDto attachmentInfo = a == null ? null : new AttachmentDto(a.getId(), a.getFileName(), a.getMediaType());
                    return new MessageWithAttachment(m.getId(), m.getOffset(), 
                            m.getUserId(), usersMap.get(m.getUserId()), m.getChatId(), 
                            attachmentInfo, m.getText(), m.getDate(), m.getDelivered(), 
                            m.getRead(), m.getEdited());
                }).collect(Collectors.toList());
    }
}
