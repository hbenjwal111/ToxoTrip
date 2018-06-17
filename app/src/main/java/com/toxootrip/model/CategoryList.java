package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 20-01-2018.
 */

public class CategoryList {

    @SerializedName("product_category")

    private List<CategoryModel> categoryModels;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;



    public CategoryList(){


    }


    public CategoryList(List<CategoryModel> categoryModels,Integer status,String message ){

        this.categoryModels = categoryModels;

        this.status = status;

        this.message = message;




    }


    public  List<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setTypeModels(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
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
