package com.atmshop.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.atmshop.MainActivity;
import com.atmshop.constant.CustomDialogListener;

import java.io.Serializable;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Swapnil on 04/03/2017.
 */

public class CommonFragment extends Fragment {
    SweetAlertDialog proDialog;
    protected CustomViewPager viewPager;
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
        new SweetAlertDialog(getMyActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Try Again !")
                .setContentText(msg)
                .show();
    }

    protected <T> T getSerializer(String key, Class<T> returnType) {
        Serializable serializedObject = null;
        Bundle bundle = null;

        try {
            bundle = getBundle();
            if (bundle.containsKey(key)) {
                serializedObject = this.getBundle().getSerializable(key);
            }
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

        return (T) serializedObject;
    }

    protected Bundle getBundle() {
        return this.getArguments();
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

    protected Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, paint);

        return scaledBitmap;

    }



    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
