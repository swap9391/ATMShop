package com.atmshop.constant;

/**
 * Created by Swapnil Sharma on 1/2/16.
 */
public interface IUrls {
     String BASE_URL = "http://35.163.66.100:8080/atmshopservice/";
    //String BASE_URL = "http://192.168.0.101:8080/vibbleInventoryService/";
    String URL_LOGIN = BASE_URL + "userlogin";
    String URL_OWNER_DETAILS = BASE_URL + "saveownerdetails";
    String URL_SHOP_LOCATION = BASE_URL + "saveshopdetails";
    String URL_SHOP_DETAILS = BASE_URL + "updateshopdetails";
    String URL_SHOP_RENT = BASE_URL + "saverentdetails";
    String URL_SHOP_PHOTOS = BASE_URL + "uploadshopimages";
    String URL_SHOP_LIST = BASE_URL + "getownerdetails";
    String URL_FULL_DETAIL = BASE_URL + "getshopdetails";
    String URL_SAVE_QSTN = BASE_URL + "savequestions";
    String URL_GENRATE_OTP = BASE_URL + "validateuser";
    String URL_VALIDATE_MOBILE = BASE_URL + "validatemobileno";
    String URL_REGISTER = BASE_URL + "saveuser";
    String IMAGE_BASE = "http://35.163.66.100:8080/content/";


}
