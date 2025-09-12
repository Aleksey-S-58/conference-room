package com.gmail.ascanada52.conference.room.entities.chat;

import java.io.Serializable;

public class ParticipantId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -425449865782224710L;

    private Integer chatId;
    private Integer userId;

    public ParticipantId(Integer chatId, Integer userId) {
        super();
        this.chatId = chatId;
        this.userId = userId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chatId == null) ? 0 : chatId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        ParticipantId other = (ParticipantId) obj;
        if (chatId == null) {
            if (other.chatId != null)
                return false;
        } else if (!chatId.equals(other.chatId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}
