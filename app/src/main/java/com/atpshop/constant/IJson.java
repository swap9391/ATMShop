package com.atpshop.constant;

/**
 * Created by Anil Sharma on 2/2/16.
 */
public interface IJson {


    //Login Screen
    String mobile_no = "mobileNumber";
    String password = "password";
    String userId = "userId";
    String active = "active";
    String otp = "password";

    //Owner

    String ownerId="ownerId";
    String owner_name="ownerName";
    String ownerMobileNo="ownerMobileNo";
    String ownerAlternativeMobileNo="ownerAlternativeMobileNo";


    //Shop Location
     String appartmentName="appartmentName";
    String area="area";
    String district="district";
    String state="state";
    String pincode="pincode";


    //Shop Details

     String shopHeight="shopHeight";
    String shopWidth="shopWidth";
    String internalWidth="internalWidth";
    String internalDepth="internalDepth";
    String carpetArea="carpetArea";
    String internalHeight="internalHeight";
    String shopId="shopId";

// Shop rent

    String rentId="rentId";
    String shopRent="shopRent";
    String negotiableRent="negotiableRent";

// Shop Photo

    String image_string="imageData";
    String imageType="imageType";
    String latitude="latitude";
    String longitude="longitude";
    String imageId="imageId";






}
