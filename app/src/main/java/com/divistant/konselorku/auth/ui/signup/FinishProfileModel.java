package com.divistant.konselorku.auth.ui.signup;

public class FinishProfileModel {
    private String status;
    private String name;
    private String role_code;
    private String progress;
    private String token;
    private String user_id;

    public FinishProfileModel(String status, String name, String role_code, String progress, String token, String user_id) {
        this.status = status;
        this.name = name;
        this.role_code = role_code;
        this.progress = progress;
        this.token = token;
        this.user_id = user_id;
    }

    public FinishProfileModel(String status, String name, String role_code, String progress, String token) {
        this.status = status;
        this.name = name;
        this.role_code = role_code;
        this.progress = progress;
        this.token = token;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
