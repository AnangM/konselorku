package com.divistant.util;

import java.util.ArrayList;
import java.util.List;

public class GeneralResponse<T> {
    private String status;
    private String message;
    private List<T> data;
    private T single_data;

    public GeneralResponse(String status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public GeneralResponse(String status, String message, T single_data) {
        this.status = status;
        this.message = message;
        this.single_data = single_data;
    }

    public T getSingle_data() {
        return single_data;
    }

    public void setSingle_data(T single_data) {
        this.single_data = single_data;
    }

    public GeneralResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public int getListSize(){
        return data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
