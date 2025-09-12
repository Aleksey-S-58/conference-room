package com.gmail.ascanada52.conference.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.Contact;
import com.gmail.ascanada52.conference.room.entities.ContactId;
import com.gmail.ascanada52.conference.room.entities.User;
import com.gmail.ascanada52.conference.room.repositories.ContactsRepository;
import com.gmail.ascanada52.conference.room.security.AuthenticationService;

@Service
public class ContactsService {

    private static final Logger logger = LoggerFactory.getLogger(ContactsService.class);
    private ContactsRepository repository;
    private UserService userService;
    private AuthenticationService authenticationService;

    public ContactsService(ContactsRepository repository, AuthenticationService authenticationService,
            UserService userService) {
        super();
        this.repository = repository;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public List<com.gmail.ascanada52.conference.room.dto.User> getAll() {
        Optional<User> u = authenticationService.getAuthenticatedUser();
        if (!u.isPresent()) {
            return new ArrayList<>();
        }
        List<Contact> contacts = repository.findByUserId(u.get().getId());
        List<Integer> ids = contacts.stream()
                .map(Contact::getContactId)
                .collect(Collectors.toList());
        return userService.findByIdList(ids);
    }

    @Transactional
    public Optional<com.gmail.ascanada52.conference.room.dto.User> addUser(Integer id) {
        Optional<User> u = authenticationService.getAuthenticatedUser();
        if (!u.isPresent()) {
            return Optional.empty();
        }
        Optional<com.gmail.ascanada52.conference.room.dto.User> newContact = userService.findById(id);
        newContact.ifPresent(nc -> {
            repository.save(new Contact(u.get().getId(), nc.getId()));
            logger.info("user id: {}, login: {} added new contact id: {}, login: {}", 
                    u.get().getId(), u.get().getLogin(), nc.getId(), nc.getLogin());
        });
        return newContact;
    }

    @Transactional
    public void removeContact(Integer id) {
        Optional<User> u = authenticationService.getAuthenticatedUser();
        u.ifPresent(user -> {
            repository.deleteById(new ContactId(user.getId(), id));
            logger.info("user id: {}, login: {} removed contact id: {}", 
                    u.get().getId(), u.get().getLogin(), id);
        });
    }
}
