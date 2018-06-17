package com.toxootrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 14-12-2017.
 */

public class SearchList  {



        @SerializedName("city")
        private List<SearchModel> searchModelList;
        @SerializedName("status")
        private int status;
    @SerializedName("message")

    private String message;



    public SearchList(List<SearchModel> searchModelList, int status,String message) {
            this.searchModelList= searchModelList;
            this.status = status;
        this.message = message;

        }

        public SearchList() {
        }

        public  List<SearchModel> getSearchModelList() {
            return searchModelList;
        }

        public void setSearchModelList(List<SearchModel> searchModelList) {
            this.searchModelList = searchModelList;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    public String getmessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    }
