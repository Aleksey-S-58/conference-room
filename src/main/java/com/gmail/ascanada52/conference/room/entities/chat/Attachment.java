package com.gmail.ascanada52.conference.room.entities.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(schema = "conference_room", name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "id")
    private Long id;
    /**
     * Actually it is MediaType.
     */
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "file_name")
    private String fileName;
    @Lob
    @Column(name = "bytes")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;

    public Attachment() {

    }

    public Attachment(Long id, String mediaType, String fileName) {
		super();
		this.id = id;
		this.mediaType = mediaType;
		this.fileName = fileName;
	}

	public Attachment(String mediaType, String fileName, byte[] bytes) {
        super();
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.bytes = bytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
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
        Attachment other = (Attachment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
