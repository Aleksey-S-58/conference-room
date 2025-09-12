package com.gmail.ascanada52.conference.room.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.chat.Attachment;

@Transactional
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Query(value = "select new com.gmail.ascanada52.conference.room.entities.chat.Attachment "
            + " (a.id, a.mediaType, a.fileName) "
            + " from Attachment a where a.id in :idList")
    List<Attachment> findAttachmentsInfoByIdList(@Param("idList") List<Long> idList);
    @Query(value = "select new com.gmail.ascanada52.conference.room.entities.chat.Attachment "
            + " (a.id, a.mediaType, a.fileName) "
            + " from Attachment a where a.id in :id")
    Optional<Attachment> findAttachmentsInfoById(@Param("id") Long id);
}
