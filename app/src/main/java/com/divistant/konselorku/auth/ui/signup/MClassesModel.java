package com.divistant.konselorku.auth.ui.signup;

public class MClassesModel {
    private String id;
    private String name;
    private String grade;
    private String school_id;

    public MClassesModel(String id, String name, String grade, String school_id) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.school_id = school_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }
}
