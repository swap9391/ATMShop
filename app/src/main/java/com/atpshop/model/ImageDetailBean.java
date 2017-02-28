package com.atpshop.model;

import static com.atpshop.constant.IJson.imageType;

/**
 * Created by Swapnil on 28/02/2017.
 */

public class ImageDetailBean {
    private int imageId;
    private String imageName;
    private String  imageType;
    private String shopId;
    FullShopDetailBean fullShopDetailBean = new FullShopDetailBean();

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public FullShopDetailBean getFullShopDetailBean() {
        return fullShopDetailBean;
    }

    public void setFullShopDetailBean(FullShopDetailBean fullShopDetailBean) {
        this.fullShopDetailBean = fullShopDetailBean;
    }
}
