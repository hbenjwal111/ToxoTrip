package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 08-02-2018.
 */

public class DashBoardList {


    @SerializedName("product_category")

    private List<DashBoardModel> dashBoardModels;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;



    public DashBoardList(){


    }


    public DashBoardList(List<DashBoardModel> dashBoardModels,Integer status,String message ){

        this.dashBoardModels = dashBoardModels;

        this.status = status;

        this.message = message;




    }


    public  List<DashBoardModel> getDashBoardModels() {
        return dashBoardModels;
    }

    public void setDashBoardModels(List<DashBoardModel> dashBoardModels) {
        this.dashBoardModels = dashBoardModels;
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
