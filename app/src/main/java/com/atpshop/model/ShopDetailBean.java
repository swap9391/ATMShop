package com.atpshop.model;

import java.io.Serializable;

import static com.atpshop.constant.IJson.shopId;

/**
 * Created by Swapnil on 05/02/2017.
 */

public class ShopDetailBean implements Serializable {
    private String shopHeight;
    private String shopWidth;
    private String internalWidth;
    private String internalDepth;
    private String carpetArea;
    private String internalHeight;
    private int shopId;
    private Integer  shopStatus;

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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
}
