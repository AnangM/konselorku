package com.divistant.konselorku.ui.dashboard;

import com.google.gson.annotations.SerializedName;

public class TitleModel {
    @SerializedName("rendered") String rendered;

    public TitleModel(String rendered) {
        this.rendered = rendered;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}
