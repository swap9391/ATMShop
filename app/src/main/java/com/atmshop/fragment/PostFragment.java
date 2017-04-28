package com.atmshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atmshop.MainActivity;
import com.atmshop.R;

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
