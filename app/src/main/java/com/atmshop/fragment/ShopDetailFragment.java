package com.atmshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.atmshop.MainActivity;
import com.atmshop.R;
import com.atmshop.common.CommonUtils;
import com.atmshop.common.FloatingActionButton;
import com.atmshop.common.StringUtils;
import com.atmshop.constant.CallWebservice;
import com.atmshop.constant.CustomDialogListener;
import com.atmshop.constant.IJson;
import com.atmshop.constant.IUrls;
import com.atmshop.constant.VolleyResponseListener;
import com.atmshop.model.FullShopDetailBean;
import com.atmshop.model.ShopDetailBean;

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class ShopDetailFragment extends CommonFragment implements View.OnClickListener, TextWatcher {

    EditText et_shutter_ht, et_shutter_wt, et_interna_ht, et_interna_wt, et_interna_depth, et_carpet_area;
    ShopDetailBean shopDetailBean;
    FullShopDetailBean fullShopDetailBean;
    boolean isUpadte = false;
    boolean connectflag = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.shop_details_layout, container, false);

        shopDetailBean = new ShopDetailBean();
        et_shutter_ht = (EditText) view.findViewById(R.id.edt_shutter_height);
        et_shutter_wt = (EditText) view.findViewById(R.id.edt_shutter_width);
        et_interna_ht = (EditText) view.findViewById(R.id.edt_internal_height);
        et_interna_wt = (EditText) view.findViewById(R.id.edt_internal_width);
        et_interna_depth = (EditText) view.findViewById(R.id.edt_internal_depth);
        et_carpet_area = (EditText) view.findViewById(R.id.edt_carpet_area);

        et_interna_wt.addTextChangedListener(this);
        et_interna_depth.addTextChangedListener(this);

        fullShopDetailBean = getMyActivity().getFullShopDetailBean();
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

    private void bindView() {
        isUpadte = true;
        et_shutter_ht.setText(fullShopDetailBean.getShopHeight());
        et_shutter_wt.setText(fullShopDetailBean.getShopWidth());
        et_interna_ht.setText(fullShopDetailBean.getInternalHeight());
        et_interna_wt.setText(fullShopDetailBean.getInternalWidth());
        et_interna_depth.setText(fullShopDetailBean.getInternalDepth());
        et_carpet_area.setText(fullShopDetailBean.getCarpetArea());
        getMyActivity().setOwnerId(fullShopDetailBean.getOwner().getOwnerId());
        if (fullShopDetailBean.getShopId() > 0) {
            getMyActivity().setShopId(fullShopDetailBean.getShopId());
        } else {
            getMyActivity().setShopId(0);
        }
    }


    private void bindModel() {
        shopDetailBean.setShopHeight(et_shutter_ht.getText().toString());
        shopDetailBean.setShopWidth(et_shutter_wt.getText().toString());
        shopDetailBean.setInternalHeight(et_interna_ht.getText().toString());
        shopDetailBean.setInternalWidth(et_interna_wt.getText().toString());
        shopDetailBean.setInternalDepth(et_interna_depth.getText().toString());
        shopDetailBean.setCarpetArea(et_carpet_area.getText().toString());

    }

    private boolean check() {

        if (shopDetailBean.getShopHeight() == null || shopDetailBean.getShopHeight().equals("")) {
            et_shutter_ht.setError("Please Enter Shutter Height");
            return false;
        }

        if (shopDetailBean.getShopWidth() == null || shopDetailBean.getShopWidth().equals("")) {
            et_shutter_wt.setError("Please Enter Shutter Width");
            return false;
        }

        if (shopDetailBean.getInternalWidth() == null || shopDetailBean.getInternalHeight().equals("")) {
            et_interna_ht.setError("Please Enter Internal Height");
            return false;
        }


        if (shopDetailBean.getInternalWidth() == null || shopDetailBean.getInternalWidth().equals("")) {
            et_interna_wt.setError("Please Enter Internal Width");
            return false;
        }

        if (shopDetailBean.getInternalDepth() == null || shopDetailBean.getInternalDepth().equals("")) {
            et_interna_depth.setError("Please Enter Internal Depth");
            return false;
        }

        if (getMyActivity().getShopId() <= 0) {
            CommonUtils.showToast(getMyActivity(), "Please Fill Shop Location Detail First");
            return false;
        }

        return true;
    }


    private void save() {
        if (connectflag == false) {
            connectflag = true;
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IJson.shopHeight, "" + shopDetailBean.getShopHeight());
            hashMap.put(IJson.shopWidth, "" + shopDetailBean.getShopWidth());
            hashMap.put(IJson.internalHeight, "" + shopDetailBean.getInternalHeight());
            hashMap.put(IJson.internalWidth, "" + shopDetailBean.getInternalWidth());
            hashMap.put(IJson.internalDepth, "" + shopDetailBean.getInternalDepth());
            hashMap.put(IJson.carpetArea, "" + shopDetailBean.getCarpetArea());
            hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());


            CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_DETAILS, hashMap, new VolleyResponseListener<ShopDetailBean>() {
                @Override
                public void onResponse(ShopDetailBean[] object) {


                    if (object[0] instanceof ShopDetailBean) {
                        for (ShopDetailBean bean : object) {
                            connectflag = false;
                            getSuccessDialog("!Congrats", "Shop Details Saved Successfully", new CustomDialogListener() {
                                @Override
                                public void onResponse() {
                                    if (isUpadte == false) {
                                        PagerFragment pager = ((PagerFragment) getParentFragment());
                                        pager.setPage(3);
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
                    connectflag = false;
                }
            }, ShopDetailBean[].class);
        }

    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        int value = CommonUtils.asInt(text, 0);
        if (editable == et_interna_depth.getEditableText()) {
            // DO STH
            if (value > 0 && !StringUtils.isEmpty(et_interna_wt.getText().toString())) {
                int totalcapet = value * CommonUtils.asInt(et_interna_wt.getText().toString(), 0);
                et_carpet_area.setText("" + totalcapet);
            }

        } else if (editable == et_interna_wt.getEditableText()) {
            // DO STH
            if (value > 0 && !StringUtils.isEmpty(et_interna_depth.getText().toString())) {
                int totalcapet = value * CommonUtils.asInt(et_interna_depth.getText().toString(), 0);
                et_carpet_area.setText("" + totalcapet);
            }
        }
    }
}
