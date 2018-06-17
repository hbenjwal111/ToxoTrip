package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 26-01-2018.
 */

public class ProductDetailList {



    @SerializedName("product_category")

    private List<ProductDetailModel> productDetailModelList;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;



    public ProductDetailList(){


    }


    public ProductDetailList(List<ProductDetailModel> productDetailModelList,Integer status,String message ){

        this.productDetailModelList = productDetailModelList;

        this.status = status;

        this.message = message;




    }


    public List<ProductDetailModel> getProductDetailModelList() {
        return productDetailModelList;
    }

    public void setProductDetailModelList(List<ProductDetailModel> productDetailModelList) {
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
