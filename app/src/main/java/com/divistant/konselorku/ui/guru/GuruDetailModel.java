package com.divistant.konselorku.ui.guru;

import java.util.List;

public class GuruDetailModel {
    List<String> edu;
    String work;

    public GuruDetailModel(List<String> edu, String work) {
        this.edu = edu;
        this.work = work;
    }

    public List<String> getEdu() {
        return edu;
    }

    public void setEdu(List<String> edu) {
        this.edu = edu;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
