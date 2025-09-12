package com.gmail.ascanada52.conference.room.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.Contact;
import com.gmail.ascanada52.conference.room.entities.ContactId;

@Transactional
@Repository
public interface ContactsRepository extends JpaRepository<Contact, ContactId> {
    List<Contact> findByUserId(Integer userId);
}
