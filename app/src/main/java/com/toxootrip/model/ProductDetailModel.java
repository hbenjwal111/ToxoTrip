package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 26-01-2018.
 */

public class ProductDetailModel {



    @SerializedName("product_category")

    private List<ProductDetailModel> productDetailModelList;



    @SerializedName("status")

    private Integer status;

    @SerializedName("message")

    private String message;


    @SerializedName("product_id")
    @Expose

    private String product_id;

    @SerializedName("product_title")
    @Expose

    private String product_title;


    @SerializedName("product_price")
    @Expose

    private String product_price;

    @SerializedName("product_url")
    @Expose

    private String product_url;

    @SerializedName("product_location")
    @Expose

    private String product_location;

    @SerializedName("product_discription")
    @Expose

    private String product_discription;


    @SerializedName("product_date")
    @Expose

    private String product_date;

    @SerializedName("phone_no")
    @Expose

    private String phone_no;

    @SerializedName("like")
    @Expose

    private String like;



    public ProductDetailModel(){


    }

    public ProductDetailModel(String like,String phone_no,String product_id,String product_title,String product_price,String product_url,String product_location,String product_discription,String product_date){


        this.product_date = product_date;
        this.product_discription = product_discription;
        this.like = like;
        this.product_id = product_id;
        this.phone_no =phone_no;
        this.product_title= product_title;
        this.product_price = product_price;
        this.product_url = product_url;
        this.product_location = product_location;
        this.productDetailModelList = productDetailModelList;

        this.status = status;

        this.message = message;

    }

    public String getProduct_id(){

        return product_id;
    }

    public void setProduct_id(String product_id){

        this.product_id = product_id;
    }

    public String getProduct_title(){

        return product_title;
    }

    public void setProduct_title(String product_title){

        this.product_title = product_title;
    }

    public String getProduct_price(){

        return product_price;
    }

    public void setProduct_price(String product_price){

        this.product_price= product_price;
    }

    public String getProduct_url(){

        return product_url;
    }

    public void setProduct_url(String product_url){

        this.product_url= product_url;
    }

    public String getProduct_location(){

        return product_location;
    }

    public void setProduct_location(String product_location){

        this.product_location= product_location;
    }


    public String getProduct_discription(){

        return product_discription;
    }

    public void setProduct_discription(String product_discription){

        this.product_discription= product_discription;
    }

    public String getProduct_date(){

        return product_date;
    }

    public void setProduct_date(String product_date){

        this.product_date= product_date;
    }
    public String getPhone_no(){

        return phone_no;
    }

    public void setPhone_no(String phone_no){

        this.phone_no= phone_no;
    }

    public String getLike(){

        return like;
    }

    public void setLike(String like){

        this.like= like;
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
