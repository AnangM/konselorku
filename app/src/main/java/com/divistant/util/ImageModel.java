package com.divistant.util;

import android.text.TextUtils;

public class ImageModel {
    private String name;
    private String url;
    private String alt;
    private int image_id;

    public ImageModel(String name, String url, String alt, int image_id) {
        this.name = name;
        this.url = url;
        this.alt = alt;
        this.image_id = image_id;
    }

    public ImageModel() {
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public boolean isNull(){
        if(this.image_id == 0 || TextUtils.isEmpty(this.url)){
            return  true;
        }else{
            return false;
        }
    }
}
