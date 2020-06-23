package com.divistant.konselorku.ui.lapor;

public class LaporModel {
    private String report_id,image_id, image_url, about,school_id;

    public LaporModel(String report_id, String image_id, String image_url, String about, String school_id) {
        this.report_id = report_id;
        this.image_id = image_id;
        this.image_url = image_url;
        this.about = about;
        this.school_id = school_id;
    }

    public LaporModel(String report_id, String image_id, String about, String school_id) {
        this.report_id = report_id;
        this.image_id = image_id;
        this.about = about;
        this.school_id = school_id;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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
}
