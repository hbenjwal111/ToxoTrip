package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 05-06-2018.
 */

public class ImageMode {



        @SerializedName("image")
        @Expose

        private String image;






        public String getImage(){

            return image;
        }

        public void setImage(String image){

            this.image = image;
        }





    }

