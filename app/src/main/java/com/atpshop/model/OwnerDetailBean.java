package com.atpshop.model;
/**
 * Created by Swapnil on 31/01/2017.
 */

public class OwnerDetailBean {
    private int ownerId;
    private String ownerName;
    private String ownerMobileNo;
    private String ownerAlternativeMobileNo;

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
