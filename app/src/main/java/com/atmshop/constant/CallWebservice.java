package com.atmshop.constant;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.atmshop.common.Connectivity;
import com.atmshop.common.CustomProgressDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Swapnil Jadhav on 13/7/16.
 */
public class CallWebservice {
    public static JSONArray jsonArray1 = null;
    public static SweetAlertDialog proDialog;


     /* used it while u get whole data in response  not only id

      @param context
      @param method
      @param url
      @param param
      @param listener
      @param aClass
      @param <T>
*/

    public static void getProgressDialog(Context context) {
        proDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        proDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        proDialog.setTitleText("Loading...");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    public static void dismissDialog() {
        if (proDialog.isShowing()) {
            proDialog.dismiss();
        }
    }

    public static void getErroDialog(String msg, Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Try Again !")
                .setContentText(msg)
                .show();
    }

    public synchronized static <T> void getWebservice(final Context context, int method, String url, final HashMap<String, String> param, final VolleyResponseListener listener, final Class<T[]> aClass) {
        if (Connectivity.isConnected(context)) {
            getProgressDialog(context);
            JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(param),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                dismissDialog();
                                JSONObject jsonObject = response;
                                String key = jsonObject.getString(IConstants.RESPONSE_KEY);
                                String message = jsonObject.getString(IConstants.RESPONSE_MESSAGE);
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                if (key.equalsIgnoreCase(IConstants.RESPONSE_SUCCESS)) {
                                    jsonArray1 = jsonObject.getJSONArray(IConstants.RESPONSE_INFO);
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();

                                    Object[] object = gson.fromJson(String.valueOf(jsonArray1), aClass);
                                    listener.onResponse(object);
                                } else if (key.equalsIgnoreCase(IConstants.RESPONSE_ERROR)) {
                                    dismissDialog();
                                    getErroDialog(message.toString(), context);
                                    listener.onError(message.toString());
                                }
                            } catch (JSONException e) {
                                dismissDialog();
                                getErroDialog(e.getMessage(), context);
                                listener.onError(e.getMessage());
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialog();
                    getErroDialog(error.toString(), context);
                    listener.onError(error.toString());
                }
            });
            AppController.getInstance().addToRequestQueue(req);

        } else {
            CustomProgressDialog.showAlertDialogMessage(context, "Check Internet Connection", "Check Internet Connection");
        }
    }

    /**
     * used this while response return only id nothing else data in single object
     *
     * @param context
     * @param method
     * @param url
     * @param param
     * @param listener
     * @param <T>
     */
    public static <T> void postWebservice(final Context context, int method, String url, final HashMap<String, String> param, final VolleyResponseListener.PostResponse listener) {
        if (Connectivity.isConnected(context)) {
            CustomProgressDialog.showDialog(context, "Please Wait");

            StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    CustomProgressDialog.dismissDialog(context);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String key = jsonObject.getString(IConstants.RESPONSE_KEY);
                        String message = jsonObject.getString(IConstants.RESPONSE_MESSAGE);
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        if (key.equalsIgnoreCase(IConstants.RESPONSE_SUCCESS)) {
                            String id = jsonObject.getString(IConstants.RESPONSE_ID);
                            listener.onResponse(id);
                        } else if (key.equalsIgnoreCase(IConstants.RESPONSE_ERROR)) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CustomProgressDialog.dismissDialog(context);
                    listener.onError(error.toString());

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap = param;
                    return hashMap;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            CustomProgressDialog.showAlertDialogMessage(context, "Check Internet Connection", "Check Internet Connection");
        }
    }
}
