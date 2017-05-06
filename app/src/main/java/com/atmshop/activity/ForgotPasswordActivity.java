package com.atmshop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.atmshop.R;
import com.atmshop.common.CommonUtils;
import com.atmshop.common.FloatingActionButton;
import com.atmshop.common.StringUtils;
import com.atmshop.constant.CallWebservice;
import com.atmshop.constant.IConstants;
import com.atmshop.constant.IJson;
import com.atmshop.constant.IUrls;
import com.atmshop.constant.VolleyResponseListener;
import com.atmshop.fragment.OtpDialogFrag;
import com.atmshop.interf.DialogResult;
import com.atmshop.model.LoginBean;

import java.util.HashMap;

/**
 * Created by root on 21/3/17.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_mobile, edt_password, edt_name;
    LoginBean loginBean;
    Button btncreate;
    private ForgotPasswordActivity TAG = ForgotPasswordActivity.this;
    boolean flager = false;
    boolean connectflag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgot_pass_layout);
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
        loginBean = new LoginBean();

    }

    private void bindModel() {
        loginBean.setMobileNumber(edt_mobile.getText().toString());
        loginBean.setPassword(edt_password.getText().toString());
    }

    @Override
    public void onClick(View view) {

    }

    private boolean check() {

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
        if (connectflag == false) {
            connectflag = true;
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IJson.mobile_no, "" + loginBean.getMobileNumber());
            CallWebservice.getWebservice(TAG, Request.Method.POST, IUrls.URL_GENRATE_OTP, hashMap, new VolleyResponseListener<LoginBean>() {
                @Override
                public void onResponse(LoginBean[] object) {
                    if (object[0] instanceof LoginBean) {
                        for (LoginBean bean : object) {
                            connectflag = false;
                            CommonUtils.insertSharedPref(TAG, IConstants.OTP, bean.getOtp().toString());
                            OtpDialogFrag dialogFragment = new OtpDialogFrag(new DialogResult() {
                                @Override
                                public void onResult(boolean flag) {
                                    if (flag == true) {
                                        flager = flag;
                                        //  Register();
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
                    connectflag = false;
                }
            }, LoginBean[].class);
        }
    }


    public void Register() {
        if (connectflag == false) {
            connectflag = true;
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IJson.userName, "" + loginBean.getUserName());
            hashMap.put(IJson.mobile_no, "" + loginBean.getMobileNumber());
            hashMap.put(IJson.password, "" + loginBean.getPassword());
            CallWebservice.getWebservice(TAG, Request.Method.POST, IUrls.URL_REGISTER, hashMap, new VolleyResponseListener<LoginBean>() {
                @Override
                public void onResponse(LoginBean[] object) {
                    if (object[0] instanceof LoginBean) {
                        for (LoginBean bean : object) {
                            connectflag = false;
                            finish();
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

}
