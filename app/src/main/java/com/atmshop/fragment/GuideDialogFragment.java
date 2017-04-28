package com.atmshop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.atmshop.R;
import com.atmshop.constant.IConstants;
import com.atmshop.interf.DialogResult;

/**
 * Created by root on 21/3/17.
 */
@SuppressLint("ValidFragment")
public class GuideDialogFragment extends DialogFragment {

    DialogResult dialogResult;
    String angle;

    public GuideDialogFragment(DialogResult dialogResult, String angle) {
        this.dialogResult = dialogResult;
        this.angle = angle;
    }

    CheckBox checkBox;
    Button btnCapture;
    ImageView imgGuide,imgAbstract;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_dialog, container);
        checkBox = (CheckBox) view.findViewById(R.id.chkTerms);
        btnCapture = (Button) view.findViewById(R.id.btn_capture);
        imgGuide = (ImageView) view.findViewById(R.id.imgguide);
        imgAbstract= (ImageView) view.findViewById(R.id.imgabstract);

        switch (angle) {
            case IConstants.LEFT_IMAGE:
                imgGuide.setImageResource(R.drawable.guide_left);
                imgAbstract.setImageResource(R.drawable.abstract_left);
                break;
            case IConstants.RIGHT_IMAGE:
                imgGuide.setImageResource(R.drawable.guide_right);
                imgAbstract.setImageResource(R.drawable.abstract_right);
                break;
            case IConstants.FRONT_IMAGE:
                imgGuide.setImageResource(R.drawable.guide_front);
                imgAbstract.setImageResource(R.drawable.abstract_front);
                break;
            case IConstants.OPPOSITE_IMAGE:
                imgGuide.setImageResource(R.drawable.guide_opp);
                imgAbstract.setImageResource(R.drawable.abstract_opposite);
                break;
            default:
                imgGuide.setImageResource(R.drawable.guide_left);
                imgAbstract.setImageResource(R.drawable.abstract_left);
                break;
        }


        getDialog().setTitle("Follow instruction");
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogResult.onResult(true);
                getDialog().dismiss();

            }
        });


        return view;
    }
}
