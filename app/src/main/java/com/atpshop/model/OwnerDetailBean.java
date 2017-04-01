package com.atpshop.model;

import java.io.Serializable;

/**
 * Created by Swapnil on 31/01/2017.
 */

public class OwnerDetailBean implements Serializable{
    private int ownerId;
    private String ownerName;
    private String ownerMobileNo;
    private String ownerAlternativeMobileNo;
    ShopDetailBean shopDetails ;

    public ShopDetailBean getShopDetailBean() {
        return shopDetails;
    }

    public void setShopDetailBean(ShopDetailBean shopDetailBean) {
        this.shopDetails = shopDetailBean;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobileNo() {
        return ownerMobileNo;
    }

    public void setOwnerMobileNo(String ownerMobileNo) {
        this.ownerMobileNo = ownerMobileNo;
    }

    public String getOwnerAlternativeMobileNo() {
        return ownerAlternativeMobileNo;
    }

    public void setOwnerAlternativeMobileNo(String ownerAlternativeMobileNo) {
        this.ownerAlternativeMobileNo = ownerAlternativeMobileNo;
    }
}
