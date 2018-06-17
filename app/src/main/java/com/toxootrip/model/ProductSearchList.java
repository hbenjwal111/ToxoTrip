package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 29-01-2018.
 */

public class ProductSearchList {

    @SerializedName("product_category")

    private List<ProductSearchModel> productDetailModelList;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;



    public ProductSearchList(){


    }


    public ProductSearchList(List<ProductSearchModel> productDetailModelList,Integer status,String message ){

        this.productDetailModelList = productDetailModelList;

        this.status = status;

        this.message = message;




    }


    public List<ProductSearchModel> getProductDetailModelList() {
        return productDetailModelList;
    }

    public void setProductDetailModelList(List<ProductSearchModel> productDetailModelList) {
        this.productDetailModelList = productDetailModelList;
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
