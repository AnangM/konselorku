package com.divistant.konselorku.auth.ui.login;

public class UserModel {
    private String status;
    private String progress;
    private String message;
    private String token;

    private String user_id;

    private String name;
    private String role_code;

    public UserModel(String status, String progress, String message, String token, String user_id, String name, String role_code) {
        this.status = status;
        this.progress = progress;
        this.message = message;
        this.token = token;
        this.user_id = user_id;
        this.name = name;
        this.role_code = role_code;
    }

    public UserModel(String status, String progress, String message, String token) {
        this.status = status;
        this.progress = progress;
        this.message = message;
        this.token = token;
    }

    public UserModel(String status, String progress, String token, String name, String role_code) {
        this.status = status;
        this.progress = progress;
        this.token = token;
        this.name = name;
        this.role_code = role_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }
}
