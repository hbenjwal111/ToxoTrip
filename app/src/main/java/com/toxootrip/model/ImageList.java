package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 11-01-2018.
 */

public class ImageList {


    @SerializedName("hotel")

    private List<ImageModel> hotelModelList;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message
;


    public ImageList(){


    }


    public ImageList(List<ImageModel> hotelModelList,Integer status , String message){

        this.hotelModelList = hotelModelList;

        this.status = status;

        this.message = message;


    }


    public  List<ImageModel> getHotelModelList() {
        return hotelModelList;
    }

    public void setHotelModelList(List<ImageModel> hotelModelList) {
        this.hotelModelList = hotelModelList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(){
        this.message =message;
    }



}
