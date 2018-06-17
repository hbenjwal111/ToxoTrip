package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 11-01-2018.
 */

public class ImageModel {


    @SerializedName("hotel_id")
    @Expose

    private String hotel_id;

    @SerializedName("hotel_name")
    @Expose

    private String hotel_name;

    @SerializedName("hotel_image")
    @Expose

    private String hotel_image;

    @SerializedName("hotel_address")
    @Expose

    private String hotel_address;

    @SerializedName("hotel_location")
    @Expose

    private String hotel_location;


    public ImageModel(){


    }

    public ImageModel(String hotel_id,String hotel_image,String hotel_address,String hotel_name){

        this.hotel_id = hotel_id;
        this.hotel_address = hotel_address;
        this.hotel_image = hotel_image;
        this.hotel_name = hotel_name;
    }

    public String getHotel_id(){

        return hotel_id;
    }

    public void setHotel_id(String hotel_id){

        this.hotel_id = hotel_id;
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
