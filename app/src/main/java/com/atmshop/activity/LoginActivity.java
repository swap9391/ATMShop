package com.atmshop.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.atmshop.MainActivity;
import com.atmshop.R;
import com.atmshop.common.CommonUtils;
import com.atmshop.common.FloatingActionButton;
import com.atmshop.common.MarshMallowPermission;
import com.atmshop.constant.CallWebservice;
import com.atmshop.constant.IConstants;
import com.atmshop.constant.IJson;
import com.atmshop.constant.IUrls;
import com.atmshop.constant.VolleyResponseListener;
import com.atmshop.model.LoginBean;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText txtUserName, txtPassword;
    Button btncreate;
    private LoginActivity TAG = LoginActivity.this;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS
    };
    boolean connectflag = false;
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
        btncreate = (Button) findViewById(R.id.btnRegister);
        ((TextView) findViewById(R.id.lblreg)).setVisibility(View.VISIBLE);
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TAG, RegisterActivity.class);
                startActivity(i);

            }
        });

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
        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(LoginActivity.this);
        verifyStoragePermissions(LoginActivity.this);
        if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage() && !marshMallowPermission.checkPermissionForReadExternalStorage()) {
            marshMallowPermission.requestPermissionForCamera();
            marshMallowPermission.requestPermissionForExternalStorage();
            marshMallowPermission.requestPermissionForReadExternalStorage();
        }

        // toolbar =(Toolbar) findViewById(R.id.toolbar);
    }


    private void bindModel() {

        loginBean.setMobileNumber(edt_mobile.getText().toString());
        loginBean.setPassword(edt_password.getText().toString());
    }


    public void Login() {
        if (connectflag == false) {
            connectflag = true;
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
                            connectflag = false;
                            CommonUtils.InsertSharedPref(LoginActivity.this, IConstants.USER_ID, bean.getUserId());
                            CommonUtils.InsertSharedPref(LoginActivity.this, IConstants.USER_NAME, bean.getUserName());
                            CommonUtils.InsertSharedPref(LoginActivity.this, IConstants.USER_MOBILE, bean.getMobileNumber());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            CommonUtils.showToast(LoginActivity.this, bean.getUserName() + " You Are Logged In Successfully!");
                            finish();
                        /*final SweetAlertDialog pDialogSuccess = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialogSuccess.setCancelable(false);
                        pDialogSuccess.setTitleText("! Congrats");
                        pDialogSuccess.setContentText("You Are Logged In Successfully");
                        pDialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();

                            }
                        });

                        pDialogSuccess.show();
*/

                        }
                    }


                }

                @Override
                public void onError(String message) {
                    connectflag = false;
                }
            }, LoginBean[].class);
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Log", "CRASHED");
    }
}
