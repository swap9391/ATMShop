package com.atmshop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.atmshop.R;
import com.atmshop.interf.DialogResult;

/**
 * Created by root on 21/3/17.
 */
@SuppressLint("ValidFragment")
public class TermDialogFragment extends DialogFragment {

    DialogResult dialogResult;
    public TermDialogFragment(DialogResult dialogResult){
        this.dialogResult=dialogResult;
    }

    CheckBox checkBox;
    Button btnAccept;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.term_dialog, container);
        checkBox=(CheckBox)view.findViewById(R.id.chkTerms);
        btnAccept=(Button) view.findViewById(R.id.btn_accept);
        getDialog().setTitle("Terms & Condition for ATM");
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()) {
                    dialogResult.onResult(true);
                    getDialog().dismiss();
                }else {
                    dialogResult.onResult(false);
                    getDialog().dismiss();
                }
            }
        });


        return view;
    }
}
