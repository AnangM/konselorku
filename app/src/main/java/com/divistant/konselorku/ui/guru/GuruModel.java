package com.divistant.konselorku.ui.guru;

import com.google.gson.annotations.SerializedName;

public class GuruModel {
    @SerializedName("id") private String id;
    @SerializedName("avatar") private String avatar;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("phone") private String phone;
    @SerializedName("about_id") private String about_id;
    @SerializedName("title") private String title;
    @SerializedName("about") private String about;
    @SerializedName("role_id") private String role_id;
    @SerializedName("role_code") private String role_code;


    public GuruModel() {
    }

    public GuruModel(String id, String avatar, String name, String email, String phone, String about_id, String title, String about, String role_id, String role_code) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.about_id = about_id;
        this.title = title;
        this.about = about;
        this.role_id = role_id;
        this.role_code = role_code;
    }

    public GuruModel(String id, String name, String email, String phone, String role_id, String role_code) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role_id = role_id;
        this.role_code = role_code;
    }

    public GuruModel(String id, String avatar, String name, String email, String phone, String role_id, String role_code) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role_id = role_id;
        this.role_code = role_code;
    }

    public GuruModel(String id, String name, String email, String phone, String about_id, String title, String about, String role_id, String role_code) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.about_id = about_id;
        this.title = title;
        this.about = about;
        this.role_id = role_id;
        this.role_code = role_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout_id() {
        return about_id;
    }

    public void setAbout_id(String about_id) {
        this.about_id = about_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }
}
