package com.atmshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.atmshop.MainActivity;
import com.atmshop.R;
import com.atmshop.activity.ForgotPasswordActivity;
import com.atmshop.common.CommonUtils;
import com.atmshop.common.FloatingActionButton;
import com.atmshop.common.StringUtils;
import com.atmshop.constant.CallWebservice;
import com.atmshop.constant.CustomDialogListener;
import com.atmshop.constant.IConstants;
import com.atmshop.constant.IJson;
import com.atmshop.constant.IUrls;
import com.atmshop.constant.VolleyResponseListener;
import com.atmshop.model.LoginBean;

import java.util.HashMap;

/**
 * Created by root on 6/5/17.
 */

public class ChangePasswordFrag extends CommonFragment {
    EditText edt_password;
    LoginBean loginBean;
    boolean flager = false;
    boolean connectflag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.change_password, container, false);
        getMyActivity().getToolbar().setTitle("Change Password");
        getMyActivity().refresh();
        LinearLayout yourframelayout = (LinearLayout)view. findViewById(R.id.floating_login);
        FloatingActionButton fabButton = new FloatingActionButton.Builder(getMyActivity(), yourframelayout)
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
                    Register();
                }

            }
        });


        edt_password = (EditText)view. findViewById(R.id.edt_password);
        loginBean = new LoginBean();


        return view;

    }


    private void bindModel() {
        loginBean.setPassword(edt_password.getText().toString());

    }

    private boolean check() {

        if (loginBean.getPassword() == null || StringUtils.isEmpty(loginBean.getPassword())) {
            CommonUtils.showToast(getMyActivity(), "Enter New Password");
            return false;
        }

        return true;
    }

    public void Register() {
        if (connectflag == false) {
            connectflag = true;
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IJson.userId, "" + CommonUtils.getSharedPref(getMyActivity(), IConstants.USER_ID));
            hashMap.put(IJson.password, "" + loginBean.getPassword());
            CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_REGISTER, hashMap, new VolleyResponseListener<LoginBean>() {
                @Override
                public void onResponse(LoginBean[] object) {
                    if (object[0] instanceof LoginBean) {
                        for (LoginBean bean : object) {
                            connectflag = false;
                             getSuccessDialog("!Congrats", "Your Password is changed", new CustomDialogListener() {
                                @Override
                                public void onResponse() {

                                }
                            });
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


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
