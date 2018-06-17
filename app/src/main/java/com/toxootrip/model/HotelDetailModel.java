package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 28-01-2018.
 */

public class HotelDetailModel {




    @SerializedName("hotel_name")
    @Expose

    private String hotel_name;

    @SerializedName("hotel_image")
    @Expose

    private String hotel_image;

    @SerializedName("hotel_address")
    @Expose

    private String hotel_address;

    @SerializedName("hotel_phone")
    @Expose

    private String hotel_phone;


    @SerializedName("hotel_location")
    @Expose

    private String hotel_location;






    public HotelDetailModel(){


    }

    public HotelDetailModel(String hotel_phone,String hotel_image,String hotel_address,String hotel_name){

        this.hotel_phone = hotel_phone;
        this.hotel_address = hotel_address;
        this.hotel_image = hotel_image;
        this.hotel_name = hotel_name;
    }
    public String getHotel_phone(){

        return hotel_phone;
    }

    public void setHotel_phone(String hotel_phone){

        this.hotel_phone = hotel_phone;
    }



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

    public String getHotel_location(){

        return hotel_location;
    }

    public void setHotel_location(String hotel_location){

        this.hotel_location = hotel_location;
    }

}
