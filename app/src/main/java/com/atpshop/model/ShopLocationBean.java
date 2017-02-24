package com.atpshop.model;

/**
 * Created by Swapnil on 05/02/2017.
 */

public class ShopLocationBean {
    private String appartmentName;
    private String area;
    private String district;
    private String state;
    private int pincode;
    private int ownerId;
    private int shopId;


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAppartmentName() {
        return appartmentName;
    }

    public void setAppartmentName(String appartmentName) {
        this.appartmentName = appartmentName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
