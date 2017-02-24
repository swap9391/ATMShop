package com.atpshop.model;

/**
 * Created by Swapnil on 07/02/2017.
 */

public class RentDetailBean {
    private int rentId;
    private int shopRent;
    private int negotiableRent;
    private int shopId;

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public int getShopRent() {
        return shopRent;
    }

    public void setShopRent(int shopRent) {
        this.shopRent = shopRent;
    }

    public int getNegotiableRent() {
        return negotiableRent;
    }

    public void setNegotiableRent(int negotiableRent) {
        this.negotiableRent = negotiableRent;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
