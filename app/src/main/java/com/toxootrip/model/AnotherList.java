package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by himanshu on 30-01-2018.
 */

public class AnotherList {

    @SerializedName("status")
    @Expose
    private Integer status;

    private List<AnotherModel> anotherModels;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<AnotherModel> getAnotherModels() {
        return anotherModels;
    }

    public void setAnotherModels(List<AnotherModel> anotherModels) {
        this.anotherModels = anotherModels;
    }

}
