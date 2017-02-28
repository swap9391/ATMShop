package com.atpshop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.FullShopDetailBean;
import com.atpshop.model.OwnerDetailBean;
import com.filippudak.ProgressPieView.ProgressPieView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 11/1/17.
 */

public class FullDetailFragment extends Fragment implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private FullDetailFragment TAG = FullDetailFragment.this;

    CircleImageView circleImageView;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    View view;
    OwnerDetailBean ownerDetailBean;
    TextView lblOwnerName, lblMobile;
    TextView lblapt, lblarea, lblState, lblCity, lblPin, lblshopht, lblshopwt, lblintht, lblintwt, lblintdept, lblexptrent, lblnegrent;
    CircleImageView circleLeft, circleRight, circleFront, circleOppos;
    private ProgressPieView mProgressPieView;
    Handler handler;
    int status = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.full_detail, container, false);

        getMyActivity().setTitle("Post Details");
        bindActivity();
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);


        ownerDetailBean = getSerializer("ownerdetail", OwnerDetailBean.class);

        // Default version
        mProgressPieView = (ProgressPieView) view.findViewById(R.id.progressPieView);
        mProgressPieView.setStrokeColor(Color.WHITE);
        mProgressPieView.setTextColor(Color.WHITE);
        mProgressPieView.setOnProgressListener(new ProgressPieView.OnProgressListener() {
            @Override
            public void onProgressChanged(int progress, int max) {
                if (!mProgressPieView.isTextShowing()) {
                    mProgressPieView.setShowText(true);
                    mProgressPieView.setShowImage(false);
                }
            }


            @Override
            public void onProgressCompleted() {
                if (!mProgressPieView.isImageShowing()) {
                    mProgressPieView.setShowImage(true);
                }
                mProgressPieView.setShowText(false);
                mProgressPieView.setImageResource(R.mipmap.ic_check_white);
            }
        });
        mProgressPieView.setProgress(status);
        mProgressPieView.setText(status + "%");
        handler = new Handler();


        Init();
        getFullDetails();

        return view;

    }


    public void Init() {
        lblOwnerName = (TextView) view.findViewById(R.id.lblOwnerName);
        lblMobile = (TextView) view.findViewById(R.id.lblmobile);
        lblapt = (TextView) view.findViewById(R.id.lblapt);
        lblarea = (TextView) view.findViewById(R.id.lblarea);
        lblState = (TextView) view.findViewById(R.id.lblState);
        lblCity = (TextView) view.findViewById(R.id.lblCity);
        lblPin = (TextView) view.findViewById(R.id.lblPin);
        lblshopht = (TextView) view.findViewById(R.id.lblshopht);
        lblshopwt = (TextView) view.findViewById(R.id.lblshopwt);
        lblintht = (TextView) view.findViewById(R.id.lblintht);
        lblintwt = (TextView) view.findViewById(R.id.lblintwt);
        lblintdept = (TextView) view.findViewById(R.id.lblintdept);
        lblexptrent = (TextView) view.findViewById(R.id.lblexptrent);
        lblnegrent = (TextView) view.findViewById(R.id.lblnegrent);
        lblnegrent = (TextView) view.findViewById(R.id.lblnegrent);
        circleLeft = (CircleImageView) view.findViewById(R.id.circleLeft);
        circleRight = (CircleImageView) view.findViewById(R.id.circleRight);
        circleFront = (CircleImageView) view.findViewById(R.id.circleFront);
        circleOppos = (CircleImageView) view.findViewById(R.id.circleOppo);


    }


    public void getFullDetails() {
        HashMap<String, String> hashMap = new HashMap<>();
        // hashMap.put(IJson.userId, "" + CommonUtils.getSharedPref(getMyActivity(),IConstants.USER_ID));
        hashMap.put(IJson.ownerId, "" + ownerDetailBean.getOwnerId());


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_FULL_DETAIL, hashMap, new VolleyResponseListener<FullShopDetailBean>() {
            @Override
            public void onResponse(FullShopDetailBean[] object) {

                if (object[0] instanceof FullShopDetailBean) {
                    for (FullShopDetailBean bean : object) {
                        bindData(bean);

                    }


                }
            }

            @Override
            public void onError(String message) {
                CommonUtils.showToast(getMyActivity(), message);
            }
        }, FullShopDetailBean[].class);


    }


    public void bindData(FullShopDetailBean bean) {

        lblOwnerName.setText(ownerDetailBean.getOwnerName());
        lblMobile.setText(ownerDetailBean.getOwnerMobileNo());
        lblapt.setText(bean.getAppartmentName());
        lblarea.setText(bean.getArea());
        lblState.setText(bean.getState());
        lblCity.setText(bean.getDistrict());
        lblPin.setText(bean.getPincode());
        lblshopht.setText(bean.getShopHeight() + " feet");
        lblshopwt.setText(bean.getShopWidth() + " feet");
        lblintht.setText(bean.getInternalHeight() + " feet");
        lblintwt.setText(bean.getInternalWidth() + " feet");
        lblintdept.setText(bean.getInternalDepth() + " feet");
        ///lblexptrent.setText(bean.getOwnerName());
        //lblnegrent.setText(bean.getOwnerName());
        setImages(circleLeft, "http://interiorikon.com/images/tn_1338724215_390108518_2-shop-for-sale-with-axis-bank-ATM-9year-basis-Mumbai.jpg");
        setImages(circleRight, "http://www.tgt-kioicho.jp/img/shop/shop1464682111_2x.jpg");
        setImages(circleFront, "https://img04.olx.in/images_olxin/276625447_1_644x461_atm-shop-and-office-for-any-showroom-digonestic-kolkata_rev003.jpg");
        setImages(circleOppos, "http://teja3.kuikr.com/i5/20170124/SHOP-AVAILABLE-FOR-RENT---MORE-SUITABLE-FOR-ATM---ak_LWBP1686246844-1485279029_lg.jpeg");

        if (ownerDetailBean.getOwnerName() != null) {
            ShowProgressDialog(30);
        } else if (bean.getDistrict() != null) {
            ShowProgressDialog(45);
        } else if (bean.getInternalWidth() != null) {
            ShowProgressDialog(60);
        }

    }

    public void setImages(CircleImageView img, final String path) {
        Picasso.with(getMyActivity()).
                load(path).
                error(R.drawable.ic_atm).
                noFade().
                placeholder(R.drawable.ic_atm).
                into(img);


        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri myUri = Uri.parse(path);
                intent.setDataAndType(myUri, "image/*");
                startActivity(intent);

            }
        });

    }


    private void bindActivity() {
        mTitle = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.main_appbar);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

    }

    protected <T> T getSerializer(String key, Class<T> returnType) {
        Serializable serializedObject = null;
        Bundle bundle = null;

        try {
            bundle = getBundle();
            if (bundle.containsKey(key)) {
                serializedObject = this.getBundle().getSerializable(key);
            }
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

        return (T) serializedObject;
    }

    protected Bundle getBundle() {
        return this.getArguments();
    }


    public void ShowProgressDialog(final int percent) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < percent) {

                    status += 1;

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            mProgressPieView.setProgress(status);
                            mProgressPieView.setText(status + "%");

                            //progressdialog.dismiss();


                        }
                    });
                }
            }
        }).start();

    }


}
