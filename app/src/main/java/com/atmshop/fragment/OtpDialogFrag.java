package com.atmshop.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.atmshop.R;
import com.atmshop.activity.RegisterActivity;
import com.atmshop.common.CommonUtils;
import com.atmshop.constant.IConstants;
import com.atmshop.interf.DialogResult;

/**
 * Created by root on 21/3/17.
 */

@SuppressLint("ValidFragment")
public class OtpDialogFrag extends DialogFragment implements View.OnClickListener {

    Button btn_resend, btn_save;
    EditText otp;
    DialogResult dialogResult;

    public OtpDialogFrag(DialogResult dialogResult) {
        this.dialogResult = dialogResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otp_layout, container);
        btn_resend = (Button) view.findViewById(R.id.btn_resend_otp);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        otp=(EditText) view.findViewById(R.id.otp);

         btn_resend.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_resend_otp:
                getDialog().dismiss();
                getMyActivity().OTP();
                break;
            case R.id.btn_save:
                if(CommonUtils.getSharedPref(IConstants.OTP,getMyActivity()).equalsIgnoreCase(otp.getText().toString())){
                    dialogResult.onResult(true);
                    getDialog().dismiss();
                }
                else {
                    CommonUtils.showToast(getMyActivity(),"Otp doesnt match!");
                }

                break;
        }
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getMyActivity()).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getMyActivity()).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                otp.setText(getOnlyNumerics(message));
                //Do whatever you want with the code here
            }
        }
    };


    public RegisterActivity getMyActivity(){
        return (RegisterActivity)getActivity();
    }

    public static String getOnlyNumerics(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length() ; i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }


}
