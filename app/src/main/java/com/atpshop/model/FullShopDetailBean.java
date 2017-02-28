package com.atpshop.model;

import static com.atpshop.constant.IJson.appartmentName;
import static com.atpshop.constant.IJson.area;
import static com.atpshop.constant.IJson.carpetArea;
import static com.atpshop.constant.IJson.district;
import static com.atpshop.constant.IJson.internalDepth;
import static com.atpshop.constant.IJson.internalHeight;
import static com.atpshop.constant.IJson.internalWidth;
import static com.atpshop.constant.IJson.latitude;
import static com.atpshop.constant.IJson.longitude;
import static com.atpshop.constant.IJson.pincode;
import static com.atpshop.constant.IJson.shopHeight;
import static com.atpshop.constant.IJson.shopId;
import static com.atpshop.constant.IJson.shopWidth;
import static com.atpshop.constant.IJson.state;

/**
 * Created by Swapnil on 27/02/2017.
 */

public class FullShopDetailBean {
    private int shopId;
    private String appartmentName;
    private String shopHeight;
    private String shopWidth;
    private String internalWidth;
    private String internalDepth;
    private String carpetArea;
    private String internalHeight;
    private String landMark;
    private String area;
    private String state;
    private String district;
    private String pincode;
    OwnerDetailBean owner = new OwnerDetailBean();

    public OwnerDetailBean getOwner() {
        return owner;
    }

    public void setOwner(OwnerDetailBean owner) {
        this.owner = owner;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getAppartmentName() {
        return appartmentName;
    }

    public void setAppartmentName(String appartmentName) {
        this.appartmentName = appartmentName;
    }

    public String getShopHeight() {
        return shopHeight;
    }

    public void setShopHeight(String shopHeight) {
        this.shopHeight = shopHeight;
    }

    public String getShopWidth() {
        return shopWidth;
    }

    public void setShopWidth(String shopWidth) {
        this.shopWidth = shopWidth;
    }

    public String getInternalWidth() {
        return internalWidth;
    }

    public void setInternalWidth(String internalWidth) {
        this.internalWidth = internalWidth;
    }

    public String getInternalDepth() {
        return internalDepth;
    }

    public void setInternalDepth(String internalDepth) {
        this.internalDepth = internalDepth;
    }

    public String getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(String carpetArea) {
        this.carpetArea = carpetArea;
    }

    public String getInternalHeight() {
        return internalHeight;
    }

    public void setInternalHeight(String internalHeight) {
        this.internalHeight = internalHeight;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
