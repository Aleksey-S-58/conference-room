package com.gmail.ascanada52.conference.room.entities;

import java.io.Serializable;

public class ContactId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -363362257228548997L;
    private Integer userId;
    private Integer contactId;

    public ContactId() {

    }

    public ContactId(Integer userId, Integer contactId) {
        super();
        this.userId = userId;
        this.contactId = contactId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
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
        ContactId other = (ContactId) obj;
        if (contactId == null) {
            if (other.contactId != null)
                return false;
        } else if (!contactId.equals(other.contactId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}
