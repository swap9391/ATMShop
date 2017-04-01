package com.atpshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.OwnerDetailBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 11/1/17.
 */

public class ShopListFragment extends CommonFragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<OwnerDetailBean> lstOwner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getMyActivity().refresh();
        View view;
        view = inflater.inflate(R.layout.shop_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        lstOwner = new ArrayList<>();
        getShopList();
        return view;

    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<OwnerDetailBean> list;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

        {
            public TextView owner_name, contact_number, contact_number2, shopid, status;

            public MyViewHolder(View view) {
                super(view);
                owner_name = (TextView) view.findViewById(R.id.lblOwnerName);
                contact_number = (TextView) view.findViewById(R.id.lblContactNo);
                contact_number2 = (TextView) view.findViewById(R.id.lblContactNo2);
                shopid = (TextView) view.findViewById(R.id.lblShopId);
                status = (TextView) view.findViewById(R.id.lblStatus);
            }

            @Override
            public void onClick(View view) {
            }
        }


        public MyAdapter(List<OwnerDetailBean> ownerlist) {
            this.list = ownerlist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_shop_item, parent, false);

            ((CardView) itemView.findViewById(R.id.card_view)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildPosition(view);
                    OwnerDetailBean ownerDetailBean = lstOwner.get(position);
                    FullDetailFragment fullDetailFragment = new FullDetailFragment();
                    Map<String, Serializable> parameters = new HashMap<String, Serializable>(2);
                    parameters.put("ownerdetail", ownerDetailBean);

                    getMyActivity().showFragment(fullDetailFragment, parameters);

                }
            });

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            OwnerDetailBean customerBean = list.get(position);
            holder.owner_name.setText(customerBean.getOwnerName());
            holder.contact_number.setText(customerBean.getOwnerMobileNo());

            if (customerBean.getShopDetailBean() != null) {

                if (customerBean.getShopDetailBean().getShopId() > 0) {
                    holder.shopid.setText("SHOP" + customerBean.getShopDetailBean().getShopId());
                } else {
                    holder.shopid.setText("");
                }

                switch (customerBean.getShopDetailBean().getShopStatus()) {
                    case 1:
                        holder.status.setText("Open");
                        break;
                    case 2:
                        holder.status.setText("Approved");
                        break;
                    case 3:
                        holder.status.setText("Dropped");
                        break;
                    default:
                        holder.status.setText("Open");
                        break;
                }

            }
            holder.contact_number.setText(customerBean.getOwnerMobileNo());
            holder.contact_number.setText(customerBean.getOwnerMobileNo());
            if (customerBean.getOwnerAlternativeMobileNo() != null) {
                holder.contact_number2.setText(customerBean.getOwnerAlternativeMobileNo());
            } else {
                holder.contact_number2.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }


    public void getShopList() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IJson.userId, "" + CommonUtils.getSharedPref(getMyActivity(), IConstants.USER_ID));
        //hashMap.put(IJson.userId, "1" );


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_LIST, hashMap, new VolleyResponseListener<OwnerDetailBean>() {
            @Override
            public void onResponse(OwnerDetailBean[] object) {

                if (object[0] instanceof OwnerDetailBean) {
                    for (OwnerDetailBean bean : object) {
                        lstOwner.add(bean);
                    }


                    myAdapter = new MyAdapter(lstOwner);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getMyActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(myAdapter);

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
        int position = recyclerView.getChildPosition(v);
        OwnerDetailBean ownerDetailBean = lstOwner.get(position);
        FullDetailFragment fullDetailFragment = new FullDetailFragment();
        Map<String, Serializable> parameters = new HashMap<String, Serializable>(2);
        parameters.put("ownerdetail", ownerDetailBean);

        getMyActivity().showFragment(fullDetailFragment, parameters);
    }

}
