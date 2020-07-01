package com.divistant.konselorku.ui.profil;

public class ProfilModel {

    private String id;
    private String email;
    private String name;
    private String gender;
    private String dob;
    private String address;
    private String phone;
    private String avatar;
    private String school_id;
    private String school_name;
    private String school_address;
    private String class_id;
    private String class_name;
    private String class_grade;

    public ProfilModel() {
    }

    public ProfilModel(String id, String email, String name, String gender, String dob, String address, String phone, String avatar, String school_id, String school_name, String school_address, String class_id, String class_name, String class_grade) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.school_id = school_id;
        this.school_name = school_name;
        this.school_address = school_address;
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_grade = class_grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_grade() {
        return class_grade;
    }

    public void setClass_grade(String class_grade) {
        this.class_grade = class_grade;
    }
}
