package com.gmail.ascanada52.conference.room.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.chat.Chat;

@Transactional
@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByOwner(Integer ownerId);
}
