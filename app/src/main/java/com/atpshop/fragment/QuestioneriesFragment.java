package com.atpshop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.common.StringUtils;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.CustomDialogListener;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.interf.DialogResult;
import com.atpshop.model.OwnerDetailBean;
import com.atpshop.model.QestionBean;

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class QuestioneriesFragment extends CommonFragment implements View.OnClickListener {


    TextView qst1, qst2, qst3, qst4, qst5, qst6, qst7, qst9, qst10;
    RadioButton rd1facebook, rd1google, rd1advertise, rd1friend, rd2ground, rd2firstfloor, rd3slap, rd3roof, rd4yes;
    RadioButton rd4no, rd5yes, rd5no, rd6yes, rd6no, rd7am, rd7pm, rd9garden, rd9school, rd9market, rd9bus, rd9hospital, rd9temples, rd9churches, rd9mosques, rd10yes, rd10no;
    EditText edt1_friend_name, edt1_friend_mobile, edt4_bank_name, edt5_bank_name, edt8_bank_name;
    QestionBean qestionBean;
    boolean flager = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.questoneries_layout, container, false);
        qestionBean = new QestionBean();
        init(view);

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

                if (flager == false) {
                    TermDialogFragment dialogFragment = new TermDialogFragment(new DialogResult() {
                        @Override
                        public void onResult(boolean flag) {
                            if (flag == true) {
                                flager = flag;
                                qestionBean.setAns8(edt8_bank_name.getText().toString());

                                if (check()) {
                                    save();
                                }
                            }
                        }
                    }
                    );
                    dialogFragment.show(getFragmentManager(), "Dialog Fragment");
                } else {
                    qestionBean.setAns8(edt8_bank_name.getText().toString());

                    if (check()) {
                        save();
                    }
                }

            }
        });


        return view;
    }

    public void init(View view) {
        qst1 = (TextView) view.findViewById(R.id.qstn1);
        qst2 = (TextView) view.findViewById(R.id.qstn2);
        qst3 = (TextView) view.findViewById(R.id.qstn3);
        qst4 = (TextView) view.findViewById(R.id.qstn4);
        qst5 = (TextView) view.findViewById(R.id.qstn5);
        qst6 = (TextView) view.findViewById(R.id.qstn6);
        qst7 = (TextView) view.findViewById(R.id.qstn7);
        qst9 = (TextView) view.findViewById(R.id.qstn9);
        qst10 = (TextView) view.findViewById(R.id.qstn10);

        edt1_friend_name = (EditText) view.findViewById(R.id.edt1_friend_name);
        edt1_friend_mobile = (EditText) view.findViewById(R.id.edt1_friend_mobile);
        edt4_bank_name = (EditText) view.findViewById(R.id.edt4_bank_name);
        edt5_bank_name = (EditText) view.findViewById(R.id.edt5_bank_name);
        edt8_bank_name = (EditText) view.findViewById(R.id.edt8_bank_name);

        edt1_friend_name.setVisibility(View.GONE);
        edt1_friend_mobile.setVisibility(View.GONE);
        edt4_bank_name.setVisibility(View.GONE);
        edt5_bank_name.setVisibility(View.GONE);

        rd1facebook = (RadioButton) view.findViewById(R.id.rd1facebook);
        rd1advertise = (RadioButton) view.findViewById(R.id.rd1advertise);
        rd1google = (RadioButton) view.findViewById(R.id.rd1google);
        rd1friend = (RadioButton) view.findViewById(R.id.rd1friend);
        rd2firstfloor = (RadioButton) view.findViewById(R.id.rd2firstfloor);
        rd2ground = (RadioButton) view.findViewById(R.id.rd2ground);
        rd3roof = (RadioButton) view.findViewById(R.id.rd3roof);
        rd3slap = (RadioButton) view.findViewById(R.id.rd3slap);
        rd4no = (RadioButton) view.findViewById(R.id.rd4no);
        rd4yes = (RadioButton) view.findViewById(R.id.rd4yes);
        rd5no = (RadioButton) view.findViewById(R.id.rd5no);
        rd5yes = (RadioButton) view.findViewById(R.id.rd5yes);
        rd6no = (RadioButton) view.findViewById(R.id.rd6no);
        rd6yes = (RadioButton) view.findViewById(R.id.rd6yes);
        rd7am = (RadioButton) view.findViewById(R.id.rd7am);
        rd7pm = (RadioButton) view.findViewById(R.id.rd7pm);
        rd9bus = (RadioButton) view.findViewById(R.id.rd9bus);
        rd9churches = (RadioButton) view.findViewById(R.id.rd9churches);
        rd9garden = (RadioButton) view.findViewById(R.id.rd9garden);
        rd9hospital = (RadioButton) view.findViewById(R.id.rd9hospital);
        rd9market = (RadioButton) view.findViewById(R.id.rd9market);
        rd9school = (RadioButton) view.findViewById(R.id.rd9school);
        rd9temples = (RadioButton) view.findViewById(R.id.rd9temples);
        rd9mosques = (RadioButton) view.findViewById(R.id.rd9mosques);
        rd10no = (RadioButton) view.findViewById(R.id.rd10no);
        rd10yes = (RadioButton) view.findViewById(R.id.rd10yes);

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

    private boolean check() {
        if (StringUtils.isEmpty(qestionBean.getAns1()) ||
                StringUtils.isEmpty(qestionBean.getAns2()) ||
                StringUtils.isEmpty(qestionBean.getAns3()) ||
                StringUtils.isEmpty(qestionBean.getAns4()) ||
                StringUtils.isEmpty(qestionBean.getAns5()) ||
                StringUtils.isEmpty(qestionBean.getAns6()) ||
                StringUtils.isEmpty(qestionBean.getAns7()) ||
                StringUtils.isEmpty(qestionBean.getAns8()) ||
                StringUtils.isEmpty(qestionBean.getAns9()) ||
                StringUtils.isEmpty(qestionBean.getAns10())
                ) {
            CommonUtils.showToast(getMyActivity(), "All questions are compulsory to answer");
            return false;
        }

        if (qestionBean.getAns1() != null
                && qestionBean.getAns1().equals("Friend")
                && StringUtils.isEmpty(edt1_friend_name.getText().toString())
                && StringUtils.isEmpty(edt1_friend_mobile.getText().toString())
                ) {
            CommonUtils.showToast(getMyActivity(), "Please enter friends name and mobile number for Question 1");
            return false;
        }

        if (qestionBean.getAns4() != null
                && qestionBean.getAns4().equals("Yes")
                && StringUtils.isEmpty(edt4_bank_name.getText().toString())) {
            CommonUtils.showToast(getMyActivity(), "Please enter bank name for Question 4");
            return false;
        }

        if (qestionBean.getAns5() != null
                && qestionBean.getAns5().equals("Yes")
                && StringUtils.isEmpty(edt5_bank_name.getText().toString())) {
            CommonUtils.showToast(getMyActivity(), "Please enter bank name for Question 5");
            return false;
        }

        return true;
    }

    private void save() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.companyReference, "" + qestionBean.getAns1());
        hashMap.put(IJson.referenceName, "" + edt1_friend_name.getText().toString());
        hashMap.put(IJson.referenceMobileNo, "" + edt1_friend_mobile.getText().toString());
        hashMap.put(IJson.shopFloor, "" + qestionBean.getAns2());
        hashMap.put(IJson.shopRoof, "" + qestionBean.getAns3());
        hashMap.put(IJson.nearAtmFirst, "" + qestionBean.getAns4());
        hashMap.put(IJson.nearAtmSecond, "" + qestionBean.getAns5());
        hashMap.put(IJson.firstBankName, "" + edt4_bank_name.getText().toString());
        hashMap.put(IJson.secondBankName, "" + edt5_bank_name.getText().toString());
        hashMap.put(IJson.shopArea, "" + qestionBean.getAns6());
        hashMap.put(IJson.highFootfall, "" + qestionBean.getAns7());
        hashMap.put(IJson.highFootfallReason, "" + qestionBean.getAns8());
        hashMap.put(IJson.shopPoi, "" + qestionBean.getAns9());
        hashMap.put(IJson.atmMachines, "" + qestionBean.getAns10());
        // hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());
        hashMap.put(IJson.questionId, "" + getMyActivity().getQuestionId());
        hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());
        // hashMap.put(IJson.userId, "1" );


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SAVE_QSTN, hashMap, new VolleyResponseListener<QestionBean>() {
            @Override
            public void onResponse(QestionBean[] object) {

                if (object[0] instanceof QestionBean) {
                    for (QestionBean bean : object) {
                        getMyActivity().setQuestionId(bean.getQuestionId());
                        getSuccessDialog("!Congrats", "Questions Submitted Successfully", new CustomDialogListener() {
                            @Override
                            public void onResponse() {
                                Intent i = new Intent(getMyActivity(), MainActivity.class);
                                startActivity(i);
                                getMyActivity().finish();
                            }
                        });


                    }
                }
            }

            @Override
            public void onError(String message) {
            }

        }, QestionBean[].class);


    }


}
