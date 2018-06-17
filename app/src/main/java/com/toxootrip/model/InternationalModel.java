package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 31-01-2018.
 */

public class InternationalModel {

    @SerializedName("country_id")
    @Expose
    private String country_id;

    @SerializedName("country_name")
    @Expose

    private String country_name;



    public  InternationalModel(){


    }

    public InternationalModel(String country_id, String country_name) {
        this.country_id= country_id;
        this.country_name = country_name;


    }


    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name(){
        return country_name;
    }

    public void setCountry_name(String country_name){

        this.country_name = country_name;
    }


}
