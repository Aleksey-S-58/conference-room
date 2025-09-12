package com.gmail.ascanada52.conference.room.dto;

public class AttachmentDto {

    private Long id;
    private String fileName;
    private String mediaType;
    private byte[] bytes;

    public AttachmentDto() {

    }

    public AttachmentDto(Long id, String fileName, String mediaType, byte[] bytes) {
        super();
        this.id = id;
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.bytes = bytes;
    }

    public AttachmentDto(String fileName, String mediaType, byte[] bytes) {
        super();
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.bytes = bytes;
    }

    public AttachmentDto(Long id, String fileName, String mediaType) {
        super();
        this.id = id;
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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
        AttachmentDto other = (AttachmentDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
