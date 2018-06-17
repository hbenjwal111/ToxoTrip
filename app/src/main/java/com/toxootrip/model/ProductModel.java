package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 22-01-2018.
 */

public class ProductModel {

    @SerializedName("status")
    @Expose

    private Integer status;

    @SerializedName("message")
    @Expose

    private String message;




    @SerializedName("user_id")
    @Expose

    private String user_id;

    @SerializedName("type_id")
    @Expose

    private String type_id;

    @SerializedName("category_id")
    @Expose

    private String category_id;

    @SerializedName("product_title")
    @Expose

    private String product_title;


    @SerializedName("product_discription")
    @Expose

    private String product_discription;


    @SerializedName("product_price")
    @Expose

    private String product_price;

    public ProductModel(){


    }

    public ProductModel(Integer status,String message,String user_id,String type_id,String category_id,String product_title,String product_discription,String product_price){

        this.user_id = user_id;
        this.type_id = type_id;
        this.category_id = category_id;
        this.product_title = product_title;
        this.product_discription = product_discription;

        this.product_price = product_price;
        this.status =status;
        this.message = message;
    }

    public Integer getStatus(){

        return status;
    }

    public void setStatus(Integer status){

        this.status = status;
    }

    public String getMessage(){

        return message;
    }

    public void setMessage(String message){

        this.message = message;
    }

    public String getUser_id(){

        return user_id;
    }

    public void setUser_id(String user_id){

        this.user_id = user_id;
    }

    public String getType_id(){

        return type_id;
    }

    public void setType_id(String type_id){

        this.type_id = type_id;
    }

    public String getCategory_id(){

        return category_id;
    }

    public void setCategory_id(String category_id){

        this.category_id = category_id;
    }

    public String getProduct_title(){

        return product_title;
    }

    public void setProduct_title(String product_title){

        this.product_title = product_title;
    }

    public String getProduct_discription(){

        return product_discription;
    }

    public void setProduct_discription(String product_discription){

        this.product_discription = product_discription;
    }

    public String getProduct_price(){

        return product_price;
    }

    public void setProduct_price(String product_price){

        this.product_price= product_price;
    }

}
