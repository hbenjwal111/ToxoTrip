package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 31-01-2018.
 */

public class IntModel {

   /* @SerializedName("hotel_id")
    @Expose

    private String hotel_id;*/
   @SerializedName("hotel")

   private List<IntModel> hotelModelList;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message
            ;

    @SerializedName("hotel_name")
    @Expose

    private String hotel_name;

    @SerializedName("country_id")
    @Expose

    private String country_id;

    @SerializedName("hotel_image")
    @Expose

    private String hotel_image;

    @SerializedName("hotel_address")
    @Expose

    private String hotel_address;

    @SerializedName("hotel_phone")
    @Expose

    private String hotel_phone;


    @SerializedName("hotel_detail")
    @Expose

    private String hotel_detail;

    @SerializedName("hotel_location")
    @Expose

    private String hotel_location;

    public IntModel(){


    }

    public IntModel(/*String hotel_id,*/String hotel_image,String hotel_address,String hotel_name,String hotel_phone,String hotel_detail){

/*
        this.hotel_id = hotel_id;
*/
        this.hotel_address = hotel_address;
        this.hotel_image = hotel_image;
        this.hotel_name = hotel_name;
        this.hotel_phone= hotel_phone;
        this.hotel_detail = hotel_detail;
    }

   /* public String getHotel_id(){

        return hotel_id;
    }

    public void setHotel_id(String hotel_id){

        this.hotel_id = hotel_id;
    }
*/
    public String getHotel_image(){

        return hotel_image;
    }

    public void setHotel_image(String hotel_image){

        this.hotel_image = hotel_image;
    }

    public String getHotel_name(){

        return hotel_name;
    }

    public void setHotel_name(String hotel_name){

        this.hotel_name = hotel_name;
    }

    public String getHotel_address(){

        return hotel_address;
    }

    public void setHotel_address(String hotel_address){

        this.hotel_address = hotel_address;
    }

    public String getHotel_phone(){

        return hotel_phone;
    }

    public void setHotel_phone(String hotel_phone){

        this.hotel_phone = hotel_phone;
    }

    public String getHotel_location(){

        return hotel_location;
    }

    public void setHotel_location(String hotel_location){

        this.hotel_location = hotel_location;
    }
    public String getHotel_detail(){

        return hotel_detail;
    }

    public void setHotel_detail(String hotel_detail){

        this.hotel_detail = hotel_detail;
    }
    public  List<IntModel> getHotelModelList() {
        return hotelModelList;
    }

    public void setHotelModelList(List<IntModel> hotelModelList) {
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

    public String getCountry_id(){

        return country_id;
    }

    public void setCountry_id(String country_id){

        this.country_id = country_id;
    }
}
