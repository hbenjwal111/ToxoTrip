package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 31-01-2018.
 */

public class MyProfileModel {

    @SerializedName("user_detail")

    private List<MyProfileModel> hotelModelList;




    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("full_name")
    @Expose

    private String full_name;

    @SerializedName("user_name")
    @Expose

    private String user_name;
    @SerializedName("email_id")
    @Expose

    private String email_id;

    @SerializedName("phone_no")
    @Expose

    private String phone_no;

    @SerializedName("profile_pic")
    @Expose

    private String profile_pic;

    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message
            ;





    public  MyProfileModel(){


    }

    public MyProfileModel(List<MyProfileModel> hotelModelList,Integer status,String message,String user_id, String full_name,String email_id,String phone_no,String profile_pic) {
        this.email_id =email_id;
        this.full_name =full_name;
        this.phone_no = phone_no;
        this.user_id = user_id;
        this.profile_pic =profile_pic;
        this.hotelModelList = hotelModelList;

        this.status = status;

        this.message = message;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name(){
        return full_name;
    }

    public void setFull_name(String full_name){

        this.full_name = full_name;
    }

    public String getEmail_id(){
        return email_id;
    }

    public void setEmail_id(String email_id){

        this.email_id = email_id;
    }

    public String getPhone_no(){
        return phone_no;
    }

    public void setPhone_no(String phone_no){

        this.phone_no = phone_no;
    }

    public String getUser_name(){
        return user_name;
    }

    public void setUser_name(String user_name){

        this.user_name = user_name;
    }

    public String getProfile_pic(){
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic){

        this.profile_pic = profile_pic;
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
