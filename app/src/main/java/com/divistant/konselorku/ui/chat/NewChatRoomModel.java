package com.divistant.konselorku.ui.chat;

public class NewChatRoomModel {

    private String room_id;
    private String target_id;

    public NewChatRoomModel(String room_id, String target_id) {
        this.room_id = room_id;
        this.target_id = target_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }
}
