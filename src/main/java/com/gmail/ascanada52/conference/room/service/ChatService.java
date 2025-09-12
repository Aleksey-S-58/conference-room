package com.gmail.ascanada52.conference.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.dto.ChatWithParticipants;
import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.entities.chat.Chat;
import com.gmail.ascanada52.conference.room.entities.chat.Participant;
import com.gmail.ascanada52.conference.room.entities.chat.ParticipantId;
import com.gmail.ascanada52.conference.room.repositories.ChatRepository;
import com.gmail.ascanada52.conference.room.repositories.ParticipantRepository;
import com.gmail.ascanada52.conference.room.security.AuthenticationService;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private ChatRepository chatRepository;
    private ParticipantRepository participantRepository;
    private AuthenticationService authenticationService;
    private UserService userService;

    public ChatService(ChatRepository chatRepository, ParticipantRepository participantRepository,
            AuthenticationService authenticationService, UserService userService) {
        super();
        this.chatRepository = chatRepository;
        this.participantRepository = participantRepository;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public List<ChatWithParticipants> getAll() {
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return new ArrayList<>();
        }
        List<Chat> chats = chatRepository.findByOwner(user.get().getId());
        return chats.stream()
                .map(c -> {
                    List<Integer> participants = c.getParticipants().stream()
                            .map(Participant::getUserId)
                            .collect(Collectors.toList());
                    return new ChatWithParticipants(c.getId(), c.getOwner(), c.getName(), participants);
                }).collect(Collectors.toList());
    }

    @Transactional
    public Optional<ChatWithParticipants> create(ChatWithParticipants chat) {
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return Optional.empty();
        }
        Chat c = new Chat(user.get().getId(), chat.getName());
        Chat entity = chatRepository.save(c);
        List<Integer> participantIds = chat.getParticipants() != null ? chat.getParticipants() : new ArrayList<>();
        List<Participant> participants = userService.findByIdList(participantIds).stream()
                .map(u -> new Participant(entity.getId(), u.getId()))
                .collect(Collectors.toList());
        List<Integer> savedParticipants = participantRepository.saveAll(participants).stream()
                .map(p -> p.getUserId())
                .collect(Collectors.toList());
        logger.info("user id: {}, login: {} created chat id: {}", user.get().getId(), user.get().getLogin(), entity.getId());
        return Optional.of(new ChatWithParticipants(entity.getId(), entity.getOwner(), entity.getName(), savedParticipants));
    }

    @Transactional
    public Optional<ChatWithParticipants> update(Integer chatId, ChatWithParticipants dto) {
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (!user.isPresent()) {
            return Optional.empty();
        }
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (!chat.isPresent() || !Objects.equals(chat.get().getOwner(), user.get().getId())) {
            return Optional.empty();
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            chat.get().setName(dto.getName());
        }
        if (dto.getParticipants() != null) {
            List<Participant> toBeRemoved = chat.get().getParticipants().stream()
                    .filter(p -> !dto.getParticipants().contains(p.getUserId()))
                    .collect(Collectors.toList());
            chat.get().getParticipants().removeAll(toBeRemoved);
            List<Participant> newParticipants = dto.getParticipants().stream()
                    .map(p -> new Participant(chat.get().getId(), p))
                    .filter(p -> !chat.get().getParticipants().contains(p))
                    .collect(Collectors.toList());
            List<Participant> persisttentParticipants = participantRepository.saveAll(newParticipants);
            chat.get().getParticipants().addAll(persisttentParticipants);
        }
        List<Integer> participants = chat.get().getParticipants().stream()
                .map(Participant::getUserId)
                .collect(Collectors.toList());
        logger.info("user id: {}, login: {} updated chat id: {}", user.get().getId(), user.get().getLogin(), chat.get().getId());
        return Optional.of(new ChatWithParticipants(chat.get().getId(), chat.get().getOwner(), chat.get().getName(), participants));
    }

    @Transactional
    public void delete(Integer chatId) {
        Optional<User> user = authenticationService.getAuthenticatedUser();
        if (user.isPresent()) {
            Optional<Chat> chat = chatRepository.findById(chatId);
            if (chat.isPresent() || Objects.equals(chat.get().getOwner(), user.get().getId())) {
                List<ParticipantId> participants = chat.get().getParticipants().stream()
                        .map(p -> new ParticipantId(p.getChatId(), p.getUserId()))
                        .collect(Collectors.toList());
                participantRepository.deleteAllById(participants);
                chatRepository.deleteById(chatId);
                logger.info("user id: {}, login: {} created chat id: {}", user.get().getId(), user.get().getLogin(), chatId);
            }
        }
    }
}
