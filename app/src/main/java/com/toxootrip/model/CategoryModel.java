package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 20-01-2018.
 */

public class CategoryModel {


    @SerializedName("Category_id")
    @Expose

    private String category_id;

    @SerializedName("category_name")
    @Expose

    private String category_name;


    @SerializedName("type_type_id")
    @Expose

    private String type_id;


    public CategoryModel(){


    }

    public CategoryModel(String category_id,String category_name,String type_id){

        this.category_id = category_id;
        this.category_name = category_name;


        this.type_id = type_id;

    }

    public String getType_id(){

        return type_id;
    }

    public void setType_id(String type_id){

        this.type_id = type_id;
    }

    public String getCategory_name(){

        return category_name;
    }

    public void setCategory_name(String category_name){

        this.category_name = category_name;
    }

    public String getCategory_id(){

        return category_id;
    }

    public void setCategory_id(String category_id){

        this.category_id = category_id;
    }




}
