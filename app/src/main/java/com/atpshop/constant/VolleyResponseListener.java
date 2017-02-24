package com.atpshop.constant;

/**
 * Created by root on 16/7/16.
 */
public interface VolleyResponseListener<T> {


    void onResponse(T[] object);



    void onError(String message);


    interface PostResponse {

        void onResponse(String id);

        void onError(String message);

    }

}
