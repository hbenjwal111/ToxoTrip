package com.toxootrip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanshu on 30-01-2018.
 */

public class AnotherModel extends ArrayList<AnotherModel> {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("hotle_detail")
    @Expose
    private List<HotelDetailModel> hotleDetail = null;
    @SerializedName("ac_banquet")
    @Expose
    private List<AcModel> acBanquet = null;
    @SerializedName("business")
    @Expose
    private List<BusinessModel> business = null;
    @SerializedName("general")
    @Expose
    private List<GenralModel> general = null;
    @SerializedName("room")
    @Expose
    private List<RoomModel> room = null;
    @SerializedName("payment")
    @Expose
    private List<PaymentModel> payment = null;
    @SerializedName("hour_opration")
    @Expose

    private List<HourModel> hour = null;
    @SerializedName("image")
    @Expose
    private List<ImageMode> image = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<HotelDetailModel> getHotleDetail() {
        return hotleDetail;
    }

    public void setHotleDetail(List<HotelDetailModel> hotleDetail) {
        this.hotleDetail = hotleDetail;
    }

    public List<ImageMode>getImage(){
        return image;
    }

    public void setImage(List<ImageMode> image){
        this.image = image;
    }
    public List<AcModel> getAcBanquet() {
        return acBanquet;
    }

    public void setAcBanquet(List<AcModel> acBanquet) {
        this.acBanquet = acBanquet;
    }

    public List<BusinessModel> getBusiness() {
        return business;
    }

    public void setBusiness(List<BusinessModel> business) {
        this.business = business;
    }

    public List<GenralModel> getGeneral() {
        return general;
    }

    public void setGeneral(List<GenralModel> general) {
        this.general = general;
    }

    public List<RoomModel> getRoom() {
        return room;
    }

    public void setRoom(List<RoomModel> room) {
        this.room = room;
    }

    public List<PaymentModel> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentModel> payment) {
        this.payment = payment;
    }

    public List<HourModel> getHour() {
        return hour;
    }

    public void setHour(List<HourModel> hour) {
        this.hour = hour;
    }


}
