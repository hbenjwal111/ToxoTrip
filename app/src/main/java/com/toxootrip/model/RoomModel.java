package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 29-01-2018.
 */

public class RoomModel {

    @SerializedName("name")
    @Expose

    private String name;


    @SerializedName("hotle_hotel_id")
    @Expose
    private String hotleHotelId;



    public String getHotleHotelId() {
        return hotleHotelId;
    }

    public void setHotleHotelId(String hotleHotelId) {
        this.hotleHotelId = hotleHotelId;
    }
    public String getName(){

        return name;
    }

    public void setName(String name){

        this.name = name;
    }
}
