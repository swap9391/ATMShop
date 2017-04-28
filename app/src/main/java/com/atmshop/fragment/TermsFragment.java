package com.atmshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.atmshop.MainActivity;
import com.atmshop.R;

/**
 * Created by Swapnil on 4/4/17.
 */

public class TermsFragment extends CommonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getMyActivity().getToolbar().setTitle("Terms and Condition");
        getMyActivity().refresh();
        View view;
        view = inflater.inflate(R.layout.term_dialog, container, false);
        ((Button) view.findViewById(R.id.btn_accept)).setVisibility(View.GONE);
        ((CheckBox) view.findViewById(R.id.chkTerms)).setVisibility(View.GONE);
        return view;

    }

    public MainActivity getMyActivity(){
        return (MainActivity)getActivity();
    }
}