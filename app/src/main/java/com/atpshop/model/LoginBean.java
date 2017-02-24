package com.atpshop.model;

/**
 * Created by Swapnil on 26/01/2017.
 */

public class LoginBean {

    //{"result":"","sessionId":"","key":"1","message":"Success","data":{"userId":1,"mobileNumber":"9561934134","password":"vai","active":1}}
    private String mobileNumber;
    private Integer otp;
    private String password;
    private Integer userId;
    private Integer active;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
