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
import com.atpshop.listners.PagerListner;
import com.atpshop.model.FullShopDetailBean;
import com.atpshop.model.OwnerDetailBean;
import com.atpshop.model.ShopLocationBean;

import java.util.HashMap;

import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.atpshop.R.id.pager;

/**
 * Created by root on 11/1/17.
 */

public class ShopLocationFragment extends CommonFragment implements View.OnClickListener {

    ShopLocationBean shopLocationBean;
    EditText et_apartment, et_street, et_city, et_state, et_pincode;
    FullShopDetailBean fullShopDetailBean;
    boolean isUpadte = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.shop_location_layout, container, false);
        fullShopDetailBean = getMyActivity().getFullShopDetailBean();
        shopLocationBean = new ShopLocationBean();
        et_apartment = (EditText) view.findViewById(R.id.edt_apartment);
        et_street = (EditText) view.findViewById(R.id.edt_area);
        et_state = (EditText) view.findViewById(R.id.edt_state);
        et_city = (EditText) view.findViewById(R.id.edt_city);
        et_pincode = (EditText) view.findViewById(R.id.edt_pin_code);

        if (fullShopDetailBean.getOwner().getOwnerId() > 0) {
            bindView();
        }

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


    public PagerFragment getParent() {
        return (PagerFragment) getParentFragment();
    }

    private void bindView() {
        isUpadte = true;
        et_apartment.setText(fullShopDetailBean.getAppartmentName());
        et_street.setText(fullShopDetailBean.getArea());
        et_city.setText(fullShopDetailBean.getDistrict());
        et_state.setText(fullShopDetailBean.getState());
        et_pincode.setText(fullShopDetailBean.getPincode());
        getMyActivity().setOwnerId(fullShopDetailBean.getOwner().getOwnerId());
        if (fullShopDetailBean.getShopId() > 0) {
            getMyActivity().setShopId(fullShopDetailBean.getShopId());
        } else {
            getMyActivity().setShopId(0);
        }
    }


    private void bindModel() {
        shopLocationBean.setAppartmentName(et_apartment.getText().toString());
        shopLocationBean.setArea(et_street.getText().toString());
        shopLocationBean.setDistrict(et_city.getText().toString());
        shopLocationBean.setState(et_state.getText().toString());
        shopLocationBean.setPincode(CommonUtils.asInt(et_pincode.getText().toString(), 0));

    }

    private boolean check() {

        if (shopLocationBean.getArea() == null || shopLocationBean.getArea().equals("")) {
            et_street.setError("Please Enter Area/Street/Gali");
            return false;
        }
        if (shopLocationBean.getState() == null || shopLocationBean.getState().equals("")) {
            et_state.setError("Please Enter District");
            return false;
        }
        if (shopLocationBean.getDistrict() == null || shopLocationBean.getDistrict().equals("")) {
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

        if (getMyActivity().getOwnerId() <= 0) {
            CommonUtils.showToast(getMyActivity(), "Please Fill Owner Detail First");
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
        hashMap.put(IJson.ownerId, "" + getMyActivity().getOwnerId());
        hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_LOCATION, hashMap, new VolleyResponseListener<ShopLocationBean>() {
            @Override
            public void onResponse(ShopLocationBean[] object) {
                if (object[0] instanceof ShopLocationBean) {
                    for (ShopLocationBean bean : object) {
                        getMyActivity().setShopId(bean.getShopId());

                        getSuccessDialog("!Congrats", "Shop Location Saved Successfully", new CustomDialogListener() {
                            @Override
                            public void onResponse() {
                                if (isUpadte == false) {
                                    PagerFragment pager = ((PagerFragment) getParentFragment());
                                    pager.setPage(2);
                                } else {
                                    FullShopDetailBean fullShopDetailBean = new FullShopDetailBean();
                                    getMyActivity().setFullShopDetailBean(fullShopDetailBean);
                                    getMyActivity().showFragment(ShopListFragment.class);
                                }
                            }
                        });


                    }
                }


            }

            @Override
            public void onError(String message) {
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
