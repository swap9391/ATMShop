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
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.OwnerDetailBean;
import com.atpshop.model.ShopLocationBean;

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class ShopLocationFragment extends Fragment implements View.OnClickListener {

    ShopLocationBean shopLocationBean;
    EditText et_apartment, et_street, et_city, et_state, et_pincode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.shop_location_layout, container, false);

        shopLocationBean = new ShopLocationBean();


        et_apartment = (EditText) view.findViewById(R.id.edt_apartment);
        et_street = (EditText) view.findViewById(R.id.edt_area);
        et_state = (EditText) view.findViewById(R.id.edt_state);
        et_city = (EditText) view.findViewById(R.id.edt_city);
        et_pincode = (EditText) view.findViewById(R.id.edt_pin_code);


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
        shopLocationBean.setAppartmentName(et_apartment.getText().toString());
        shopLocationBean.setArea(et_street.getText().toString());
        shopLocationBean.setDistrict(et_city.getText().toString());
        shopLocationBean.setState(et_state.getText().toString());
        shopLocationBean.setPincode(CommonUtils.asInt(et_pincode.getText().toString(), 0));

    }

    private boolean check() {

        if (shopLocationBean.getArea() == null || StringUtils.isEmpty(shopLocationBean.getArea())) {
            et_street.setError("Please Enter Area/Street/Gali");
            return false;
        }
        if (shopLocationBean.getState() == null || StringUtils.isEmpty(shopLocationBean.getState())) {
            et_state.setError("Please Enter District");
            return false;
        }
        if (shopLocationBean.getDistrict() == null || StringUtils.isEmpty(shopLocationBean.getDistrict())) {
            et_city.setError("Please Enter City");
            return false;
        }


        if (shopLocationBean.getPincode() <= 0) {
            et_pincode.setError("Please Enter Pin Code");
            return false;
        }


        if (shopLocationBean.getPincode() > 0 && et_pincode.length() < 6) {
            et_pincode.setError("Minimum 6 digits Pin Code required");
            return false;
        }

        return true;
    }


    private void save() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.appartmentName, "" + shopLocationBean.getAppartmentName());
        hashMap.put(IJson.area, "" + shopLocationBean.getArea());
        hashMap.put(IJson.district, "" + shopLocationBean.getDistrict());
        hashMap.put(IJson.state, "" + shopLocationBean.getState());
        hashMap.put(IJson.pincode, "" + shopLocationBean.getPincode());
        hashMap.put(IJson.userId, "" + CommonUtils.getSharedPref(getMyActivity(), IConstants.USER_ID));
        //hashMap.put(IJson.ownerId,""+getMyActivity().getOwnerId());
        hashMap.put(IJson.ownerId,"28");
        hashMap.put(IJson.shopId,""+getMyActivity().getShopId());



        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_LOCATION, hashMap, new VolleyResponseListener<ShopLocationBean>() {
            @Override
            public void onResponse(ShopLocationBean[] object) {


                if (object[0] instanceof ShopLocationBean) {
                    for (ShopLocationBean bean : object) {
                        CommonUtils.showToast(getMyActivity(), "Shop Location Saved Successfully");
                        getMyActivity().setShopId(bean.getShopId());
                    }
                }


            }

            @Override
            public void onError(String message) {
                CommonUtils.showToast(getMyActivity(), message);
            }
        }, ShopLocationBean[].class);


    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }
}
