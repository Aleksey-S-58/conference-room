package com.gmail.ascanada52.conference.room.entities.chat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(schema = "conference_room", name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "id")
    private Long id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "attachment_id")
    private Long attachmentId;
    @Column(name = "chat_id")
    private Integer chatId;
    @Column(name = "message_order")
    private Long offset;
    @Column(name = "message_text")
    private String text;
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(name = "message_timestamp", nullable = false)
    private Date date;
    @Column(name = "delivered")
    private Boolean delivered = false;
    @Column(name = "read")
    private Boolean read = false;
    @Column(name = "edited")
    private Boolean edited = false;

    public Message() {

    }

    public Message(Integer userId, Long attachmentId, Integer chatId, String text) {
        super();
        this.userId = userId;
        this.attachmentId = attachmentId;
        this.chatId = chatId;
        this.text = text;
    }

    public Message(Integer userId, Long attachmentId, Integer chatId, String text, Date date) {
        super();
        this.userId = userId;
        this.attachmentId = attachmentId;
        this.chatId = chatId;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
