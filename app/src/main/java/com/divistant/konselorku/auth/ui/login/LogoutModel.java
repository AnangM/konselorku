package com.divistant.konselorku.auth.ui.login;

public class LogoutModel {
    private String message;

    public LogoutModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
