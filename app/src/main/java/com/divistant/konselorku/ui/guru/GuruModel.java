package com.divistant.konselorku.ui.guru;

import com.google.gson.annotations.SerializedName;

public class GuruModel {
    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("channel") private String channel;
    @SerializedName("about") private String about;
    @SerializedName("education") private String education;
    @SerializedName("work") private String work;

    public GuruModel() {
    }

    public GuruModel(String id, String name, String email, String channel, String about,
                     String education, String work) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.channel = channel;
        this.about = about;
        this.education = education;
        this.work = work;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public String toString(){
        if(this.name != null){
            return this.name;
        }else{
            return "No Data!";
        }
    }
}
