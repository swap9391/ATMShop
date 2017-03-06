package com.atpshop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.volley.Response;
import com.atpshop.MainActivity;
import com.atpshop.constant.CustomDialogListener;
import com.atpshop.constant.VolleyResponseListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Swapnil on 04/03/2017.
 */

public class CommonFragment extends Fragment {
    SweetAlertDialog proDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void getProgressDialog() {
        proDialog = new SweetAlertDialog(getMyActivity(), SweetAlertDialog.PROGRESS_TYPE);
        proDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        proDialog.setTitleText("Loading");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    protected void DismissDialog() {
        if (proDialog.isShowing()) {
            proDialog.dismiss();
        }
    }

    protected void getErroDialog(String msg) {
        DismissDialog();
        new SweetAlertDialog(getMyActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Try Again !")
                .setContentText(msg)
                .show();
    }

    protected void getSuccessDialog(final String title, final String content, final CustomDialogListener listener) {

        final SweetAlertDialog pDialogSuccess = new SweetAlertDialog(getMyActivity(), SweetAlertDialog.SUCCESS_TYPE);

        pDialogSuccess.setCancelable(false);
        pDialogSuccess.setTitleText(title);
        pDialogSuccess.setContentText(content);
        pDialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
// reuse previous dialog instance
                sDialog.dismiss();
                listener.onResponse();
            }
        });

        pDialogSuccess.show();
    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
