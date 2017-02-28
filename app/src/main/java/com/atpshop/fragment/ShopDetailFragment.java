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
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.ShopDetailBean;

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class ShopDetailFragment extends Fragment implements View.OnClickListener {

    EditText et_shutter_ht,et_shutter_wt,et_interna_ht,et_interna_wt,et_interna_depth,et_carpet_area;
    ShopDetailBean shopDetailBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.shop_details_layout, container, false);

        shopDetailBean= new ShopDetailBean();
        et_shutter_ht = (EditText) view.findViewById(R.id.edt_shutter_height);
        et_shutter_wt= (EditText) view.findViewById(R.id.edt_shutter_width);
        et_interna_ht = (EditText) view.findViewById(R.id.edt_internal_height);
        et_interna_wt = (EditText) view.findViewById(R.id.edt_internal_width);
        et_interna_depth = (EditText) view.findViewById(R.id.edt_internal_depth);
        et_carpet_area = (EditText) view.findViewById(R.id.edt_carpet_area);


        LinearLayout yourframelayout = (LinearLayout)view. findViewById(R.id.floating_login);
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
                if(check()){
                    save();
                }

            }
        });


        return view;

    }




    private void bindModel(){
        shopDetailBean.setShopHeight(et_shutter_ht.getText().toString() );
        shopDetailBean.setShopWidth(et_shutter_wt.getText().toString());
        shopDetailBean.setInternalHeight(et_interna_ht.getText().toString());
        shopDetailBean.setInternalWidth(et_interna_wt.getText().toString());
        shopDetailBean.setInternalDepth(et_interna_depth.getText().toString());
        shopDetailBean.setCarpetArea(et_carpet_area.getText().toString());

    }

    private boolean check(){

        if(shopDetailBean.getShopHeight()==null &&StringUtils.isEmpty(shopDetailBean.getShopHeight())  ){
            et_shutter_ht.setError("Please Enter Shutter Height");
             return false;
        }

        if(shopDetailBean.getShopWidth()==null &&StringUtils.isEmpty(shopDetailBean.getShopWidth())  ){
            et_shutter_wt.setError("Please Enter Shutter Width");
            return false;
        }

        if(shopDetailBean.getInternalWidth()==null &&StringUtils.isEmpty(shopDetailBean.getInternalHeight())  ){
            et_interna_ht.setError("Please Enter Internal Height");
            return false;
        }


        if(shopDetailBean.getInternalWidth()==null &&StringUtils.isEmpty(shopDetailBean.getInternalWidth())  ){
            et_interna_wt.setError("Please Enter Internal Width");
            return false;
        }

        if(shopDetailBean.getInternalDepth()==null &&StringUtils.isEmpty(shopDetailBean.getInternalDepth())  ){
            et_interna_depth.setError("Please Enter Internal Depth");
            return false;
        }

        return  true;
    }


    private void save(){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.shopHeight, "" + shopDetailBean.getShopHeight());
        hashMap.put(IJson.shopWidth, "" + shopDetailBean.getShopWidth());
        hashMap.put(IJson.internalHeight, "" + shopDetailBean.getInternalHeight());
        hashMap.put(IJson.internalWidth, "" + shopDetailBean.getInternalWidth());
        hashMap.put(IJson.internalDepth, "" + shopDetailBean.getInternalDepth());
        hashMap.put(IJson.carpetArea, "" + shopDetailBean.getCarpetArea());
        hashMap.put(IJson.shopId,""+ getMyActivity().getShopId() );


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_DETAILS, hashMap, new VolleyResponseListener<ShopDetailBean>() {
            @Override
            public void onResponse(ShopDetailBean[] object) {


                if (object[0] instanceof ShopDetailBean) {
                    for (ShopDetailBean bean : object) {
                        CommonUtils.showToast(getMyActivity(),"Shop Details Saved Successfully");

                        PagerFragment pager = ((PagerFragment) getParentFragment());
                        pager.setPage(3);

                    }
                }


            }

            @Override
            public void onError(String message) {
                CommonUtils.showToast(getMyActivity(),message);
            }
        }, ShopDetailBean[].class);


    }




    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }
}
