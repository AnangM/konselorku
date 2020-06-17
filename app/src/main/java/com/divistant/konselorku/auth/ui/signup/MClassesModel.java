package com.divistant.konselorku.auth.ui.signup;

public class MClassesModel {
    private String m_classes_id;
    private String name;
    private String grade;
    private String school_id;

    public MClassesModel(String m_classes_id, String name, String grade, String school_id) {
        this.m_classes_id = m_classes_id;
        this.name = name;
        this.grade = grade;
        this.school_id = school_id;
    }

    public String getM_classes_id() {
        return m_classes_id;
    }

    public void setM_classes_id(String m_classes_id) {
        this.m_classes_id = m_classes_id;
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
