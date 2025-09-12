package com.gmail.ascanada52.conference.room.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.chat.Participant;
import com.gmail.ascanada52.conference.room.entities.chat.ParticipantId;

@Transactional
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, ParticipantId> {

}
