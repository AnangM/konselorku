package com.divistant.util;

public class ImageModel {
    private String name;
    private String url;
    private String alt;

    public ImageModel(String name, String url, String alt) {
        this.name = name;
        this.url = url;
        this.alt = alt;
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
}
