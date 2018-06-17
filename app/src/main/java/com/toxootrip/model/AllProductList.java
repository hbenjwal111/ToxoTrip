package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 25-01-2018.
 */

public class AllProductList {

    @SerializedName("product_category")

    private List<AllProductModel> allProductModels;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;



    public AllProductList(){


    }


    public AllProductList(List<AllProductModel> allProductModels,Integer status,String message ){

        this.allProductModels = allProductModels;

        this.status = status;

        this.message = message;




    }


    public  List<AllProductModel> getAllProductModels() {
        return allProductModels;
    }

    public void setAllProductModels(List<AllProductModel> allProductModels) {
        this.allProductModels = allProductModels;
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

    public void setMessage(String message){

        this.message = message;
    }






}
