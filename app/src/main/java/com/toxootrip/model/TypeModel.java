package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 19-01-2018.
 */

public class TypeModel {


    @SerializedName("type_id")
    @Expose

    private String type_id;

    @SerializedName("type_name")
    @Expose

    private String type_name;

    @SerializedName("category_id")
    @Expose

    private String category_id;

    @SerializedName("category_name")
    @Expose

    private String category_name;



    public TypeModel(){


    }

    public TypeModel(String type_id,String type_name,String category_id,String category_name){

        this.type_id = type_id;
        this.type_name = type_name;
        this.category_id = category_id;
        this.category_name = category_name;

    }

    public String getType_id(){

        return type_id;
    }

    public void setType_id(String type_id){

        this.type_id = type_id;
    }

    public String getType_name(){

        return type_name;
    }

    public void setType_name(String type_name){

        this.type_name = type_name;
    }

    public String getCategory_id(){

        return category_id;
    }

    public void setCategory_id(String category_id){

        this.category_id = category_id;
    }

    public String getCategory_name(){

        return category_name;
    }

    public void setCategory_name(String category_name){

        this.category_name = category_name;
    }

}
