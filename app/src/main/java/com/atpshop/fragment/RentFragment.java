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
import com.atpshop.model.OwnerDetailBean;
import com.atpshop.model.RentDetailBean;
import com.atpshop.model.ShopLocationBean;

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class RentFragment extends CommonFragment implements View.OnClickListener {

    RentDetailBean rentDetailBean;
    EditText edt_expected, edt_negot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.rent_detail_layout, container, false);

        rentDetailBean = new RentDetailBean();


        edt_expected = (EditText) view.findViewById(R.id.edt_expected_rent);
        edt_negot = (EditText) view.findViewById(R.id.edt_negotiable);


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
                bindModel();
                if (check()) {
                    save();
                }
            }
        });


        return view;

    }


    private void bindModel() {
        rentDetailBean.setShopRent(CommonUtils.asInt(edt_expected.getText().toString(), 0));
        rentDetailBean.setNegotiableRent(CommonUtils.asInt(edt_negot.getText().toString(), 0));
    }

    private boolean check() {


        if (rentDetailBean.getShopRent() <= 0) {
            edt_expected.setError("Enter Expected Rent");
            return false;
        }

        if (rentDetailBean.getNegotiableRent() <= 0) {
            edt_negot.setError("Enter Final Rent");
            return false;
        }
        if (rentDetailBean.getShopRent() < rentDetailBean.getNegotiableRent()) {
            edt_negot.setError("Please enter less than expected amount");
            return false;
        }

        if (getMyActivity().getShopId() <= 0) {
            CommonUtils.showToast(getMyActivity(), "Please Fill Shop Location Detail First");
            return false;
        }
        return true;
    }


    private void save() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.rentId, "" + getMyActivity().getRentId());
        hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());
        hashMap.put(IJson.shopRent, "" + rentDetailBean.getShopRent());
        hashMap.put(IJson.negotiableRent, "" + rentDetailBean.getNegotiableRent());

        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_RENT, hashMap, new VolleyResponseListener<RentDetailBean>() {
            @Override
            public void onResponse(RentDetailBean[] object) {

                if (object[0] instanceof RentDetailBean) {
                    for (RentDetailBean bean : object) {

                        getMyActivity().setRentId(bean.getRentId());
                        getSuccessDialog("!Congrats", "Rent Details Saved Successfully", new CustomDialogListener() {
                            @Override
                            public void onResponse() {
                                PagerFragment pager = ((PagerFragment) getParentFragment());
                                pager.setPage(4);

                            }
                        });

                    }
                }

            }

            @Override
            public void onError(String message) {
            }
        }, RentDetailBean[].class);


    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }
}
