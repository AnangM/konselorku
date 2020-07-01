package com.divistant.konselorku.ui.lapor;

import com.divistant.util.ImageModel;

import java.util.List;

public class LaporanModel {
    private String id;
    private String about;
    private String user_id;
    private String user_name;
    private String school_name;
    private String school_id;
    private String created_at;
    private List<ImageModel> images;

    public LaporanModel(String id, String about, String user_id, String user_name, String school_name, String school_id, String created_at, List<ImageModel> images) {
        this.id = id;
        this.about = about;
        this.user_id = user_id;
        this.user_name = user_name;
        this.school_name = school_name;
        this.school_id = school_id;
        this.created_at = created_at;
        this.images = images;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }
}
