package com.atpshop.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.common.StringUtils;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.fragment.OtpDialogFrag;
import com.atpshop.fragment.TermDialogFragment;
import com.atpshop.interf.DialogResult;
import com.atpshop.model.LoginBean;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by root on 21/3/17.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_mobile, edt_password, edt_name;
    LoginBean loginBean;
    Button btncreate;
    private RegisterActivity TAG = RegisterActivity.this;
    boolean flager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);
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
                bindModel();
                if (check()) {
                    OTP();
                }

            }
        });


        edt_mobile = (EditText) findViewById(R.id.edt_mobile_no);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_name.setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.lblreg)).setVisibility(View.GONE);
        btncreate = (Button) findViewById(R.id.btnRegister);
        btncreate.setVisibility(View.GONE);
        loginBean = new LoginBean();

    }

    private void bindModel() {

        loginBean.setMobileNumber(edt_mobile.getText().toString());
        loginBean.setPassword(edt_password.getText().toString());
        loginBean.setUserName(edt_name.getText().toString());
    }

    @Override
    public void onClick(View view) {

    }

    private boolean check() {
        if (loginBean.getUserName() == null || StringUtils.isEmpty(loginBean.getUserName())) {
            CommonUtils.showToast(TAG, "Enter Your Name");
            return false;
        }
        if (loginBean.getMobileNumber() == null || StringUtils.isEmpty(loginBean.getMobileNumber())) {
            CommonUtils.showToast(TAG, "Enter Mobile Number");
            return false;
        }

        if (loginBean.getMobileNumber() != null && loginBean.getMobileNumber().length() < 10) {
            CommonUtils.showToast(TAG, "Enter 10 digit Mobile Number");
            return false;
        }

        if (loginBean.getPassword() == null || StringUtils.isEmpty(loginBean.getPassword())) {
            CommonUtils.showToast(TAG, "Enter Password");
            return false;
        }

        return true;
    }


    public void OTP() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.mobile_no, "" + loginBean.getMobileNumber());
        CallWebservice.getWebservice(TAG, Request.Method.POST, IUrls.URL_GENRATE_OTP, hashMap, new VolleyResponseListener<LoginBean>() {
            @Override
            public void onResponse(LoginBean[] object) {
                if (object[0] instanceof LoginBean) {
                    for (LoginBean bean : object) {
                        CommonUtils.insertSharedPref(TAG, IConstants.OTP, bean.getOtp().toString());
                        OtpDialogFrag dialogFragment = new OtpDialogFrag(new DialogResult() {
                            @Override
                            public void onResult(boolean flag) {
                                if (flag == true) {
                                    flager = flag;
                                    Register();
                                }
                            }
                        }
                        );
                        dialogFragment.setCancelable(false);
                        dialogFragment.show(getSupportFragmentManager(), "Dialog Fragment");


                    }
                }
            }

            @Override
            public void onError(String message) {
            }
        }, LoginBean[].class);

    }


    public void Register() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.userName, "" + loginBean.getUserName());
        hashMap.put(IJson.mobile_no, "" + loginBean.getMobileNumber());
        hashMap.put(IJson.password, "" + loginBean.getPassword());
        CallWebservice.getWebservice(TAG, Request.Method.POST, IUrls.URL_REGISTER, hashMap, new VolleyResponseListener<LoginBean>() {
            @Override
            public void onResponse(LoginBean[] object) {
                if (object[0] instanceof LoginBean) {
                    for (LoginBean bean : object) {
                        finish();
                    }
                }
            }

            @Override
            public void onError(String message) {
            }
        }, LoginBean[].class);

    }

}
