package com.atpshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.atpshop.MainActivity;
import com.atpshop.R;

/**
 * Created by Swapnil on 4/4/17.
 */

public class AboutFragment extends CommonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.about_us, container, false);
        getMyActivity().getToolbar().setTitle("About Application");
        getMyActivity().refresh();

        return view;

    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}