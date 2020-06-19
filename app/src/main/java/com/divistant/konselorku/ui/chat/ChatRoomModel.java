package com.divistant.konselorku.ui.chat;

public class ChatRoomModel {
    private String room_name = "D";
    private String room_id;
    private boolean is_active;
    private String room_image;

    public ChatRoomModel(String room_name, String room_id, boolean is_active, String room_image) {
        this.room_name = room_name;
        this.room_id = room_id;
        this.is_active = is_active;
        this.room_image = room_image;
    }

    public ChatRoomModel(String room_name, String room_id, boolean is_active) {
        this.room_name = room_name;
        this.room_id = room_id;
        this.is_active = is_active;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getRoom_image() {
        return room_image;
    }

    public void setRoom_image(String room_image) {
        this.room_image = room_image;
    }
}
