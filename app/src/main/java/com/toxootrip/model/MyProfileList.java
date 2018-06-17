package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 31-01-2018.
 */

public class MyProfileList {

    @SerializedName("user_detail")

    private List<MyProfileModel> hotelModelList;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message
            ;


    public MyProfileList(){


    }


    public MyProfileList(List<MyProfileModel> hotelModelList, Integer status , String message){

        this.hotelModelList = hotelModelList;

        this.status = status;

        this.message = message;


    }


    public  List<MyProfileModel> getHotelModelList() {
        return hotelModelList;
    }

    public void setHotelModelList(List<MyProfileModel> hotelModelList) {
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
