package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by himanshu on 12-12-2017.
 */

public class SearchModel {




        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("city_name")
        @Expose

        private String city_name;



        public  SearchModel(){


        }

        public SearchModel(int id, String city_name) {
            this.id = id;
            this.city_name = city_name;


        }


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCity_name(){
            return city_name;
        }

        public void setCity_name(String city_name){

            this.city_name = city_name;
        }



}
