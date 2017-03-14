package com.atpshop.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.CustomProgressDialog;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.constant.AppController;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText txtUserName, txtPassword;
    private LoginActivity TAG = LoginActivity.this;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS
    };

    EditText edt_mobile, edt_password;
    LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        if (CommonUtils.getUserId(this) > 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        edt_mobile = (EditText) findViewById(R.id.edt_mobile_no);
        edt_password = (EditText) findViewById(R.id.edt_password);
        loginBean = new LoginBean();

        txtUserName = (EditText) findViewById(R.id.edt_mobile_no);
        txtPassword = (EditText) findViewById(R.id.edt_password);
        LinearLayout yourframelayout = (LinearLayout) findViewById(R.id.floating_login);
        FloatingActionButton fabButton = new FloatingActionButton.Builder(this, yourframelayout)
                .withDrawable(getResources().getDrawable(R.mipmap.ic_check_white))
                .withButtonColor(Color.parseColor("#e65100"))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(2, 2, 2, 2)
                .create();


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent1);*/
                Login();

            }
        });

        verifyStoragePermissions(LoginActivity.this);
        // toolbar =(Toolbar) findViewById(R.id.toolbar);
    }


    private void bindModel() {

        loginBean.setMobileNumber(edt_mobile.getText().toString());
        loginBean.setPassword(edt_password.getText().toString());
    }


    public void Login() {

        bindModel();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.mobile_no, "" + loginBean.getMobileNumber());
        hashMap.put(IJson.password, "" + loginBean.getPassword());
        hashMap.put(IJson.userId, "0");
        hashMap.put(IJson.active, "1");


        CallWebservice.getWebservice(TAG, Request.Method.POST, IUrls.URL_LOGIN, hashMap, new VolleyResponseListener<LoginBean>() {
            @Override
            public void onResponse(LoginBean[] object) {


                if (object[0] instanceof LoginBean) {
                    for (LoginBean bean : object) {
                        CommonUtils.InsertSharedPref(LoginActivity.this, IConstants.USER_ID, bean.getUserId());
                        CommonUtils.InsertSharedPref(LoginActivity.this, IConstants.USER_NAME, bean.getUserName());
                        CommonUtils.InsertSharedPref(LoginActivity.this, IConstants.USER_MOBILE, bean.getMobileNumber());
                        final SweetAlertDialog pDialogSuccess = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialogSuccess.setCancelable(false);
                        pDialogSuccess.setTitleText("! Congrats");
                        pDialogSuccess.setContentText("You Are Logged In Successfully");
                        pDialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        pDialogSuccess.show();


                    }
                }

                /*
                if (object[0] instanceof LoginBean) {
                    for (LoginBean bean : object) {

                        SharedPreferences.Editor editor = LoginActivity.this.getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE).edit();
                        String hotelCode = bean.getHotel_code();
                        editor.putString("hotelcode", hotelCode);
                        editor.putInt("userid", bean.getUser_id());
                        editor.commit();

                        CommonUtils.insertSharedPref(LoginActivity.this, IConstants.SH_FIRSTTIME,"FIRST");


                        Toast.makeText(getApplicationContext(), "Welcome " + bean.getFirst_name() + " " + bean.getLast_name(), Toast.LENGTH_LONG).show();
                        //USER_ID = bean.getJs_id();
                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent1);
                        // startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
                        finish();
                    }
                }
*/
            }

            @Override
            public void onError(String message) {
            }
        }, LoginBean[].class);


    }


    public static void verifyStoragePermissions(Activity activity) {

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionSMS = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);

        if (permission != PackageManager.PERMISSION_GRANTED && permissionSMS != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


    private void makeJsonObjectRequest() {


// Post params to be sent to the server
        bindModel();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.mobile_no, "" + loginBean.getMobileNumber());
        hashMap.put(IJson.password, "" + loginBean.getPassword());
        hashMap.put(IJson.userId, "0");
        hashMap.put(IJson.active, "1");

        JsonObjectRequest req = new JsonObjectRequest(IUrls.URL_LOGIN, new JSONObject(hashMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
    }

}
