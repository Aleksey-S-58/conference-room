package com.gmail.ascanada52.conference.room.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.chat.Message;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatIdAndOffsetGreaterThanOrderByOffset(@Param("chatId") Integer chatId, @Param("offset") Long offset);
    Page<Message> findByChatIdAndOffsetLessThanOrderByOffsetDesc(@Param("chatId") Integer chatId, @Param("offset") Long offset, Pageable pageable);
    @Query("select count(m) > 0 from Message m where m.attachmentId = :attachmentId")
    boolean existsByAttachmentId(@Param("attachmentId") Long attachmentId);
    @Query("select Max(m.offset) from Message m where m.chatId = :chatId")
    Optional<Long> findMaxOffset(@Param("chatId") Integer chatId);
}
