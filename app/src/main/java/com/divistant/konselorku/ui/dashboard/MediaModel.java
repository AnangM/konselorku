package com.divistant.konselorku.ui.dashboard;

import com.google.gson.annotations.SerializedName;

public class MediaModel {
    @SerializedName("id") private String id;
    @SerializedName("post") private String post;
    @SerializedName("guid") private MediaGUID media;

    public MediaModel(String id, String post, MediaGUID media) {
        this.id = id;
        this.post = post;
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMedia() {
        return media.getRendered();
    }

    public void setMedia(MediaGUID media) {
        this.media = media;
    }

    private class MediaGUID{
        @SerializedName("rendered") private String rendered;

        public MediaGUID(String rendered) {
            this.rendered = rendered;
        }

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }
    }
}


