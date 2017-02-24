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

import java.util.HashMap;

/**
 * Created by root on 11/1/17.
 */

public class PostFragment extends Fragment implements View.OnClickListener {

    private PostFragment TAG = PostFragment.this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.post_layout, container, false);

        getMyActivity().setTitle("Post Details");


        return view;

    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }




}
