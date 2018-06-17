package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 08-02-2018.
 */

public class DashBoardModel {

    @SerializedName("Category_id")
    @Expose

    private String category_id;

    @SerializedName("category_name")
    @Expose

    private String category_name;

    @SerializedName("icon")
    @Expose

    private String icon;



    public DashBoardModel(){


    }

    public DashBoardModel(String category_id,String category_name,String icon){

this.category_id= category_id;
        this.category_name= category_name;
        this.icon = icon;
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

    public String getIcon(){

        return icon;
    }

    public void setIcon(String icon){

        this.icon = icon;
    }
}
