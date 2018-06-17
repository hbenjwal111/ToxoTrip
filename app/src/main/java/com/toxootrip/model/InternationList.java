package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 31-01-2018.
 */

public class InternationList {

    @SerializedName( "country")
    private List<InternationalModel> searchModelList;
    @SerializedName("status")
    private int status;
    @SerializedName("message")

    private String message;



    public InternationList(List<InternationalModel> searchModelList, int status,String message) {
        this.searchModelList= searchModelList;
        this.status = status;
        this.message = message;

    }

    public InternationList() {
    }

    public List<InternationalModel> getSearchModelList() {
        return searchModelList;
    }

    public void setSearchModelList(List<InternationalModel> searchModelList) {
        this.searchModelList = searchModelList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getmessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
