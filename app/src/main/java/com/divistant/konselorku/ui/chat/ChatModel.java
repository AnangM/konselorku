package com.divistant.konselorku.ui.chat;

public class ChatModel {
    private String message;
    private String senderUid;
    private Boolean is_available;
    private Long timestamp;

    private String image;
    private String key;

    public ChatModel(){}

    public ChatModel(String message, String senderUid, Boolean is_available, Long timestamp) {
        this.message = message;
        this.senderUid = senderUid;
        this.is_available = is_available;
        this.timestamp = timestamp;
    }

    public ChatModel(String message, String senderUid, Boolean is_available, Long timestamp, String image) {
        this.message = message;
        this.senderUid = senderUid;
        this.is_available = is_available;
        this.timestamp = timestamp;
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
