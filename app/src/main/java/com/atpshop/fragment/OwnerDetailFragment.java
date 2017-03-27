package com.atpshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.activity.LoginActivity;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.common.StringUtils;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.CustomDialogListener;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.LoginBean;
import com.atpshop.model.OwnerDetailBean;
import com.filippudak.ProgressPieView.ProgressPieView;

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class OwnerDetailFragment extends CommonFragment implements View.OnClickListener {

    EditText et_owner_name, et_contact_1, et_contact_2;
    OwnerDetailBean ownerDetailBean;
    private OwnerDetailFragment TAG = OwnerDetailFragment.this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.owner_detail_layout, container, false);


        ownerDetailBean = new OwnerDetailBean();

        et_owner_name = (EditText) view.findViewById(R.id.edt_owner_name);
        et_contact_1 = (EditText) view.findViewById(R.id.edt_owner_contact1);
        et_contact_2 = (EditText) view.findViewById(R.id.edt_owner_contact2);

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
        ownerDetailBean.setOwnerName(et_owner_name.getText().toString());
        ownerDetailBean.setOwnerMobileNo(et_contact_1.getText().toString());
        ownerDetailBean.setOwnerAlternativeMobileNo(et_contact_2.getText().toString());
    }

    private boolean check() {

        if (ownerDetailBean.getOwnerName() == null || ownerDetailBean.getOwnerName().equals("")) {
            et_owner_name.setError("Please Enter Owner Name");
            return false;
        }

        if (ownerDetailBean.getOwnerMobileNo() == null || ownerDetailBean.getOwnerMobileNo().equals("")) {
            et_contact_1.setError("Please Enter Contact Number");
            return false;
        }

        if (ownerDetailBean.getOwnerMobileNo() != null && !ownerDetailBean.getOwnerMobileNo().equals("") && ownerDetailBean.getOwnerMobileNo().length() < 10) {
            et_contact_1.setError("Minimum 10 digits required");
            return false;
        }

        return true;
    }


    private void save() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.owner_name, "" + ownerDetailBean.getOwnerName());
        hashMap.put(IJson.ownerMobileNo, "" + ownerDetailBean.getOwnerMobileNo());
        hashMap.put(IJson.ownerAlternativeMobileNo, "" + ownerDetailBean.getOwnerAlternativeMobileNo());
        hashMap.put(IJson.userId, "" + CommonUtils.getSharedPref(getMyActivity(), IConstants.USER_ID));
        hashMap.put(IJson.ownerId, "" + getMyActivity().getOwnerId());
        // hashMap.put(IJson.userId, "1" );


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_OWNER_DETAILS, hashMap, new VolleyResponseListener<OwnerDetailBean>() {
            @Override
            public void onResponse(OwnerDetailBean[] object) {

                if (object[0] instanceof OwnerDetailBean) {
                    for (OwnerDetailBean bean : object) {
                        getMyActivity().setOwnerId(bean.getOwnerId());
                        getSuccessDialog("!Congrats", "Owner Detail Saved Successfully", new CustomDialogListener() {
                            @Override
                            public void onResponse() {
                                PagerFragment pager = ((PagerFragment) getParentFragment());
                                pager.setPage(1);
                            }
                        });


                    }
                }
            }

            @Override
            public void onError(String message) {
            }

        }, OwnerDetailBean[].class);


    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }


}
