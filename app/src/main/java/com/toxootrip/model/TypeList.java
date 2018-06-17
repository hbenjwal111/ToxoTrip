package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by himanshu on 19-01-2018.
 */

public class TypeList {

    @SerializedName("product_type")

    private ArrayList<TypeModel> typeModels;



    @SerializedName("status")

    private Integer status;




    public TypeList(){


    }


    public TypeList(ArrayList<TypeModel> typeModels,Integer status ){

        this.typeModels = typeModels;

        this.status = status;




    }


    public  ArrayList<TypeModel> getTypeModels() {
        return typeModels;
    }

    public void setTypeModels(ArrayList<TypeModel> typeModels) {
        this.typeModels = typeModels;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }





}


