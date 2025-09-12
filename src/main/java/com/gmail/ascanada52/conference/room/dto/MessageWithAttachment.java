package com.gmail.ascanada52.conference.room.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageWithAttachment {

    private Long id;
    private Long messageOrder;
    private Integer userId;
    private String userName;
    private Integer chatId;
    private AttachmentDto attachment;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date date;
    private Boolean delivered;
    private Boolean read;
    private Boolean edited;

    public MessageWithAttachment() {

    }

    public MessageWithAttachment(Long id, Long messageOrder, Integer userId, Integer chatId, AttachmentDto attachment,
            String text) {
        super();
        this.id = id;
        this.messageOrder = messageOrder;
        this.userId = userId;
        this.chatId = chatId;
        this.attachment = attachment;
        this.text = text;
        date = new Date();
    }

    public MessageWithAttachment(Long id, Long messageOrder, Integer userId, String userName, Integer chatId,
            AttachmentDto attachment, String text, Date date) {
        super();
        this.id = id;
        this.messageOrder = messageOrder;
        this.userId = userId;
        this.userName = userName;
        this.chatId = chatId;
        this.attachment = attachment;
        this.text = text;
        this.date = date;
    }

    public MessageWithAttachment(Long id, Long messageOrder, Integer userId, String userName, Integer chatId,
            AttachmentDto attachment, String text, Date date, Boolean delivered, Boolean read, Boolean edited) {
        super();
        this.id = id;
        this.messageOrder = messageOrder;
        this.userId = userId;
        this.userName = userName;
        this.chatId = chatId;
        this.attachment = attachment;
        this.text = text;
        this.date = date;
        this.delivered = delivered;
        this.read = read;
        this.edited = edited;
    }

    public MessageWithAttachment(Long messageOrder, Integer userId, Integer chatId, AttachmentDto attachment,
            String text) {
        super();
        this.messageOrder = messageOrder;
        this.userId = userId;
        this.chatId = chatId;
        this.attachment = attachment;
        this.text = text;
        date = new Date();
    }

    public MessageWithAttachment(Long id, Long messageOrder, Integer userId, Integer chatId, String text) {
        super();
        this.id = id;
        this.messageOrder = messageOrder;
        this.userId = userId;
        this.chatId = chatId;
        this.text = text;
        date = new Date();
    }

    public MessageWithAttachment(Integer userId, Integer chatId, AttachmentDto attachment, String text) {
        super();
        this.userId = userId;
        this.chatId = chatId;
        this.attachment = attachment;
        this.text = text;
        date = new Date();
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
        MessageWithAttachment other = (MessageWithAttachment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageOrder() {
        return messageOrder;
    }

    public void setMessageOrder(Long messageOrder) {
        this.messageOrder = messageOrder;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public AttachmentDto getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDto attachment) {
        this.attachment = attachment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
