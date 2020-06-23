package com.divistant.util;

import java.util.ArrayList;
import java.util.List;

public class GeneralResponse<T> {
    private boolean success;
    private String message;
    private List<T> data;
    private T single_data;

    public GeneralResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public GeneralResponse(boolean success, String message, List<T> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public GeneralResponse(boolean success, String message, T single_data) {
        this.success = success;
        this.message = message;
        this.single_data = single_data;
    }

    public T getSingle_data() {
        return single_data;
    }

    public void setSingle_data(T single_data) {
        this.single_data = single_data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
