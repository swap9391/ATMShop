package com.atpshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.model.QestionBean;

/**
 * Created by root on 11/1/17.
 */

public class QuestioneriesFragment extends Fragment implements View.OnClickListener {


    TextView qst1, qst2, qst3, qst4, qst5, qst6, qst7, qst9, qst10;
    RadioButton rd1facebook, rd1google, rd1advertise, rd1friend, rd2ground, rd2firstfloor, rd3slap, rd3roof, rd4yes;
    RadioButton rd4no, rd5yes, rd5no, rd6yes, rd6no, rd7am, rd7pm, rd9garden, rd9school, rd9market, rd9bus, rd9hospital, rd9temples, rd9churches, rd9mosques, rd10yes, rd10no;
    EditText edt1_friend_name, edt1_friend_mobile, edt4_bank_name, edt5_bank_name, edt8_bank_name;
    QestionBean qestionBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.questoneries_layout, container, false);
        qestionBean = new QestionBean();
        init();

        LinearLayout yourframelayout = (LinearLayout) view.findViewById(R.id.floating_login);
        FloatingActionButton fabButton = new FloatingActionButton.Builder(getMyActivity(), yourframelayout)
                .withDrawable(getResources().getDrawable(R.mipmap.ic_check_white))
                .withButtonColor(Color.parseColor("#00C853"))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(2, 2, 2, 2)
                .create();


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* bindModel();
                if (check()) {
                    save();
                }*/
            }
        });


        return view;
    }

    public void init() {
        qst1 = (TextView) getView().findViewById(R.id.qstn1);
        qst2 = (TextView) getView().findViewById(R.id.qstn2);
        qst3 = (TextView) getView().findViewById(R.id.qstn3);
        qst4 = (TextView) getView().findViewById(R.id.qstn4);
        qst5 = (TextView) getView().findViewById(R.id.qstn5);
        qst6 = (TextView) getView().findViewById(R.id.qstn6);
        qst7 = (TextView) getView().findViewById(R.id.qstn7);
        qst9 = (TextView) getView().findViewById(R.id.qstn9);
        qst10 = (TextView) getView().findViewById(R.id.qstn10);

        edt1_friend_name = (EditText) getView().findViewById(R.id.edt1_friend_name);
        edt1_friend_mobile = (EditText) getView().findViewById(R.id.edt1_friend_mobile);
        edt4_bank_name = (EditText) getView().findViewById(R.id.edt4_bank_name);
        edt5_bank_name = (EditText) getView().findViewById(R.id.edt5_bank_name);
        edt8_bank_name = (EditText) getView().findViewById(R.id.edt8_bank_name);

        edt1_friend_name.setVisibility(View.GONE);
        edt1_friend_mobile.setVisibility(View.GONE);
        edt4_bank_name.setVisibility(View.GONE);
        edt5_bank_name.setVisibility(View.GONE);
        edt8_bank_name.setVisibility(View.GONE);

        rd1facebook = (RadioButton) getView().findViewById(R.id.rd1facebook);
        rd1advertise = (RadioButton) getView().findViewById(R.id.rd1advertise);
        rd1google = (RadioButton) getView().findViewById(R.id.rd1google);
        rd1friend = (RadioButton) getView().findViewById(R.id.rd1friend);
        rd2firstfloor = (RadioButton) getView().findViewById(R.id.rd2firstfloor);
        rd2ground = (RadioButton) getView().findViewById(R.id.rd2ground);
        rd3roof = (RadioButton) getView().findViewById(R.id.rd3roof);
        rd3slap = (RadioButton) getView().findViewById(R.id.rd3slap);
        rd4no = (RadioButton) getView().findViewById(R.id.rd4no);
        rd4yes = (RadioButton) getView().findViewById(R.id.rd4yes);
        rd5no = (RadioButton) getView().findViewById(R.id.rd5no);
        rd5yes = (RadioButton) getView().findViewById(R.id.rd5yes);
        rd6no = (RadioButton) getView().findViewById(R.id.rd6no);
        rd6yes = (RadioButton) getView().findViewById(R.id.rd6yes);
        rd7am = (RadioButton) getView().findViewById(R.id.rd7am);
        rd7pm = (RadioButton) getView().findViewById(R.id.rd7pm);
        rd9bus = (RadioButton) getView().findViewById(R.id.rd9bus);
        rd9churches = (RadioButton) getView().findViewById(R.id.rd9churches);
        rd9garden = (RadioButton) getView().findViewById(R.id.rd9garden);
        rd9hospital = (RadioButton) getView().findViewById(R.id.rd9hospital);
        rd9market = (RadioButton) getView().findViewById(R.id.rd9market);
        rd9school = (RadioButton) getView().findViewById(R.id.rd9school);
        rd9temples = (RadioButton) getView().findViewById(R.id.rd9temples);
        rd9mosques = (RadioButton) getView().findViewById(R.id.rd9mosques);
        rd10no = (RadioButton) getView().findViewById(R.id.rd10no);
        rd10yes = (RadioButton) getView().findViewById(R.id.rd10yes);

        rd1facebook.setOnClickListener(this);
        rd1advertise.setOnClickListener(this);
        rd1google.setOnClickListener(this);
        rd1friend.setOnClickListener(this);
        rd2firstfloor.setOnClickListener(this);
        rd2ground.setOnClickListener(this);
        rd3roof.setOnClickListener(this);
        rd3slap.setOnClickListener(this);
        rd4no.setOnClickListener(this);
        rd4yes.setOnClickListener(this);
        rd5no.setOnClickListener(this);
        rd5yes.setOnClickListener(this);
        rd6no.setOnClickListener(this);
        rd6yes.setOnClickListener(this);
        rd7am.setOnClickListener(this);
        rd7pm.setOnClickListener(this);
        rd9bus.setOnClickListener(this);
        rd9churches.setOnClickListener(this);
        rd9garden.setOnClickListener(this);
        rd9hospital.setOnClickListener(this);
        rd9market.setOnClickListener(this);
        rd9school.setOnClickListener(this);
        rd9temples.setOnClickListener(this);
        rd9mosques.setOnClickListener(this);
        rd10no.setOnClickListener(this);
        rd10yes.setOnClickListener(this);
    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rd1facebook:
                qestionBean.setAns1("Facebook");
                edt1_friend_name.setVisibility(View.GONE);
                edt1_friend_mobile.setVisibility(View.GONE);
                break;
            case R.id.rd1advertise:
                qestionBean.setAns1("Advertisement");
                edt1_friend_name.setVisibility(View.GONE);
                edt1_friend_mobile.setVisibility(View.GONE);
                break;
            case R.id.rd1friend:
                qestionBean.setAns1("Friend");
                edt1_friend_name.setVisibility(View.VISIBLE);
                edt1_friend_mobile.setVisibility(View.VISIBLE);
                break;
            case R.id.rd1google:
                qestionBean.setAns1("Google Search");
                edt1_friend_name.setVisibility(View.GONE);
                edt1_friend_mobile.setVisibility(View.GONE);
                break;
            case R.id.rd2firstfloor:
                qestionBean.setAns2("First Floor");
                break;
            case R.id.rd2ground:
                qestionBean.setAns2("Ground Floor");
                break;
            case R.id.rd3roof:
                qestionBean.setAns3("Roof");
                break;
            case R.id.rd3slap:
                qestionBean.setAns3("Slap");
                break;
            case R.id.rd4no:
                edt4_bank_name.setVisibility(View.GONE);
                qestionBean.setAns4("No");
                break;
            case R.id.rd4yes:
                edt4_bank_name.setVisibility(View.VISIBLE);
                qestionBean.setAns4("Yes");
                break;
            case R.id.rd5no:
                edt5_bank_name.setVisibility(View.GONE);
                qestionBean.setAns5("No");
                break;
            case R.id.rd5yes:
                edt5_bank_name.setVisibility(View.VISIBLE);
                qestionBean.setAns5("Yes");
                break;
            case R.id.rd6no:
                qestionBean.setAns6("No");
                break;
            case R.id.rd6yes:
                qestionBean.setAns6("Yes");
                break;
            case R.id.rd7am:
                qestionBean.setAns7("Am");
                break;
            case R.id.rd7pm:
                qestionBean.setAns7("Pm");
                break;
            case R.id.rd9bus:
                qestionBean.setAns9("Bus");
                break;
            case R.id.rd9churches:
                qestionBean.setAns9("Church");
                break;
            case R.id.rd9garden:
                qestionBean.setAns9("Garden");
                break;
            case R.id.rd9hospital:
                qestionBean.setAns9("Hospital");
                break;
            case R.id.rd9market:
                qestionBean.setAns9("Vegetable Market");
                break;
            case R.id.rd9mosques:
                qestionBean.setAns9("Mosque");
                break;
            case R.id.rd9school:
                qestionBean.setAns9("School");
                break;
            case R.id.rd9temples:
                qestionBean.setAns9("Temple");
                break;
            case R.id.rd10no:
                qestionBean.setAns10("No");
                break;
            case R.id.rd10yes:
                qestionBean.setAns10("Yes");
                break;

        }
    }
}
