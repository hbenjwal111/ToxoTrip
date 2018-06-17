package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 23-02-2018.
 */

public class SearchProductList {
    @SerializedName("product_category")

    private List<SearchProductModel> allProductModels;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;



    public SearchProductList(){


    }


    public SearchProductList(List<SearchProductModel> allProductModels,Integer status,String message ){

        this.allProductModels = allProductModels;

        this.status = status;

        this.message = message;




    }


    public List<SearchProductModel> getAllProductModels() {
        return allProductModels;
    }

    public void setAllProductModels(List<SearchProductModel> allProductModels) {
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
