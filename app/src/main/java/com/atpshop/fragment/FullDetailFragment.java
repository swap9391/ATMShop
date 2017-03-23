package com.atpshop.fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.StringUtils;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.CustomDialogListener;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.CustomerFiles;
import com.atpshop.model.FullShopDetailBean;
import com.atpshop.model.OwnerDetailBean;
import com.filippudak.ProgressPieView.ProgressPieView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static android.R.attr.path;
import static com.atpshop.R.id.btnFront;
import static com.atpshop.R.id.btnLeft;
import static com.atpshop.R.id.btnRight;
import static com.atpshop.R.id.imgFront;
import static com.atpshop.R.id.imgLeft;
import static com.atpshop.R.id.imgRight;

/**
 * Created by root on 11/1/17.
 */

public class FullDetailFragment extends CommonFragment implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private FullDetailFragment TAG = FullDetailFragment.this;
    FullShopDetailBean fullShopDetailBean;
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
    TextView lblapt, lblarea, lblState, lblCity, lblPin, lblshopht, lblshopwt, lblintht, lblintwt, lblintdept, lblCarpetArea, lblexptrent, lblnegrent;
    CardView cardLoc, cardDetail, cardRent;
    CircleImageView circleLeft, circleRight, circleFront, circleOppos;
    ImageButton editLocation, editrent, editShopDetail;
    private ProgressPieView mProgressPieView;
    Handler handler;
    int status = 0;
    //images
    private int REQUEST_PHOTO_LEFT = 101, REQUEST_PHOTO_RIGHT = 102, REQUEST_PHOTO_FRO = 103, REQUEST_PHOTO_OPP = 104;
    Uri fileView;
    int imageCount = 0, sentCount = 0;
    File file;
    List<CustomerFiles> dataT;
    CustomerFiles customerFiles;

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

        //images
        dataT = new ArrayList<CustomerFiles>();
        customerFiles = new CustomerFiles();

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
        lblCarpetArea = (TextView) view.findViewById(R.id.lblCarpetArea);
        lblexptrent = (TextView) view.findViewById(R.id.lblexptrent);
        lblnegrent = (TextView) view.findViewById(R.id.lblnegrent);
        lblnegrent = (TextView) view.findViewById(R.id.lblnegrent);
        circleLeft = (CircleImageView) view.findViewById(R.id.circleLeft);
        circleRight = (CircleImageView) view.findViewById(R.id.circleRight);
        circleFront = (CircleImageView) view.findViewById(R.id.circleFront);
        circleOppos = (CircleImageView) view.findViewById(R.id.circleOppo);
        editLocation = (ImageButton) view.findViewById(R.id.imgloceddit);
        editShopDetail = (ImageButton) view.findViewById(R.id.imgshopddit);
        editrent = (ImageButton) view.findViewById(R.id.imgrentddit);
        cardLoc = (CardView) view.findViewById(R.id.card_loc);
        cardRent = (CardView) view.findViewById(R.id.card_rent);
        cardDetail = (CardView) view.findViewById(R.id.card_shop);
        circleLeft.setOnClickListener(this);
        circleOppos.setOnClickListener(this);
        circleRight.setOnClickListener(this);
        circleFront.setOnClickListener(this);
        editrent.setOnClickListener(this);
        editShopDetail.setOnClickListener(this);
        editLocation.setOnClickListener(this);

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
                        fullShopDetailBean = bean;
                        bindData();
                    }
                }
            }

            @Override
            public void onError(String message) {

                getMyActivity().showFragment(ShopListFragment.class);
            }
        }, FullShopDetailBean[].class);


    }


    public void bindData() {


        lblOwnerName.setText(ownerDetailBean.getOwnerName());
        lblMobile.setText(ownerDetailBean.getOwnerMobileNo());
        if (fullShopDetailBean.getAppartmentName() != null) {
            lblapt.setText(fullShopDetailBean.getAppartmentName());
            lblarea.setText(fullShopDetailBean.getArea());
            lblState.setText(fullShopDetailBean.getState());
            lblCity.setText(fullShopDetailBean.getDistrict());
            lblPin.setText(fullShopDetailBean.getPincode());
        } else {
            cardLoc.setVisibility(View.GONE);
        }
        if (fullShopDetailBean.getShopHeight() != null) {
            lblshopht.setText(fullShopDetailBean.getShopHeight() + " feet");
            lblshopwt.setText(fullShopDetailBean.getShopWidth() + " feet");
            lblintht.setText(fullShopDetailBean.getInternalHeight() + " feet");
            lblintwt.setText(fullShopDetailBean.getInternalWidth() + " feet");
            lblintdept.setText(fullShopDetailBean.getInternalDepth() + " feet");
            lblCarpetArea.setText(fullShopDetailBean.getCarpetArea() + " feet");
        } else {
            cardDetail.setVisibility(View.GONE);
        }
        if (fullShopDetailBean.getRent() != null && fullShopDetailBean.getRent().getShopRent() > 0) {
            lblexptrent.setText(getResources().getString(R.string.Rs) + fullShopDetailBean.getRent().getShopRent());
            lblnegrent.setText(getResources().getString(R.string.Rs) + fullShopDetailBean.getRent().getNegotiableRent());
        } else {
            cardRent.setVisibility(View.GONE);
        }
        if (fullShopDetailBean.getShopImages().size() > 0) {
            setImages(circleLeft, fullShopDetailBean.getShopImages().get(0).getImageName() != null ? fullShopDetailBean.getShopImages().get(0).getImageName() : null);
        } else {
            setImages(circleLeft, null);
        }
        if (fullShopDetailBean.getShopImages().size() > 1) {
            setImages(circleRight, fullShopDetailBean.getShopImages().get(1).getImageName() != null ? fullShopDetailBean.getShopImages().get(1).getImageName() : null);
        } else {
            setImages(circleRight, null);
        }
        if (fullShopDetailBean.getShopImages().size() > 2) {
            setImages(circleFront, fullShopDetailBean.getShopImages().get(2).getImageName() != null ? fullShopDetailBean.getShopImages().get(2).getImageName() : null);
        } else {
            setImages(circleFront, null);
        }
        if (fullShopDetailBean.getShopImages().size() > 3) {
            setImages(circleOppos, fullShopDetailBean.getShopImages().get(3).getImageName() != null ? fullShopDetailBean.getShopImages().get(3).getImageName() : null);
        } else {
            setImages(circleOppos, null);
        }


        int count = 0;
        if (ownerDetailBean.getOwnerName() != null) {
            count++;
        }
        if (fullShopDetailBean.getAppartmentName() != null) {
            count++;
        }
        if (fullShopDetailBean.getInternalWidth() != null) {
            count++;
        }
        if (fullShopDetailBean.getRent() != null && fullShopDetailBean.getRent().getShopRent() > 0) {
            count++;
        }
        if (fullShopDetailBean.getShopImages() != null && fullShopDetailBean.getShopImages().size() > 0) {
            count++;
        }


        switch (count) {
            case 1:
                ShowProgressDialog(30);
                break;
            case 2:
                ShowProgressDialog(45);
                break;
            case 3:
                ShowProgressDialog(60);
                break;
            case 4:
                ShowProgressDialog(75);
                break;
            case 5:
                ShowProgressDialog(85);
                break;
            default:
                ShowProgressDialog(0);


        }

    }

    public void setImages(CircleImageView img, final String path) {
        Picasso.with(getMyActivity()).
                load(IUrls.IMAGE_BASE + path).
                error(R.drawable.ic_atm).
                noFade().
                resize(200, 200).
                placeholder(R.drawable.ic_atm).
                into(img);

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

    /*private void performSelection(final String uripath, final int recode) {
        String[] labels = new String[]{"Show", "Update"};
        AlertDialog dlg = new AlertDialog.Builder(getActivity()).setItems(
                labels, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0: {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                Uri myUri = Uri.parse(IUrls.IMAGE_BASE + uripath);
                                intent.setDataAndType(myUri, "image*//*");
                                startActivity(intent);
                                break;
                            }
                            case 1: {
                                Intent cameraIntent1 = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                getMyActivity().startActivityForResult(cameraIntent1, recode);
                            }
                        }

                    }
                }).create();
        dlg.show();

    }
*/
    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    public void setImage(String urlpath) {
        try {
            MimeTypeMap myMime = MimeTypeMap.getSingleton();
            Intent newIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = myMime.getMimeTypeFromExtension(urlpath);
            Uri myUri = Uri.parse(IUrls.IMAGE_BASE + urlpath);
            newIntent.setDataAndType(myUri,mimeType);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(newIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getMyActivity(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
            }


/*

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri myUri = Uri.parse(IUrls.IMAGE_BASE + urlpath);
            intent.setDataAndType(myUri, "image*//**//*");
            startActivity(intent);*/
        } catch (Exception e) {

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circleLeft:
                if (fullShopDetailBean.getShopImages().size() > 0 && fullShopDetailBean.getShopImages().get(0).getImageName() != null && !StringUtils.isEmpty(fullShopDetailBean.getShopImages().get(0).getImageName())) {
                    setImage(fullShopDetailBean.getShopImages().get(0).getImageName());
                }
                break;
            case R.id.circleRight:
                if (fullShopDetailBean.getShopImages().size() > 0 && fullShopDetailBean.getShopImages().get(1).getImageName() != null && !StringUtils.isEmpty(fullShopDetailBean.getShopImages().get(1).getImageName())) {
                    setImage(fullShopDetailBean.getShopImages().get(1).getImageName());
                }

                break;
            case R.id.circleFront:
                if (fullShopDetailBean.getShopImages().size() > 0 && fullShopDetailBean.getShopImages().get(2).getImageName() != null && !StringUtils.isEmpty(fullShopDetailBean.getShopImages().get(2).getImageName())) {
                    setImage(fullShopDetailBean.getShopImages().get(2).getImageName());
                }
                break;
            case R.id.circleOppo:
                if (fullShopDetailBean.getShopImages().size() > 0 && fullShopDetailBean.getShopImages().get(3).getImageName() != null && !StringUtils.isEmpty(fullShopDetailBean.getShopImages().get(3).getImageName())) {
                    setImage(fullShopDetailBean.getShopImages().get(3).getImageName());
                }
                break;

            case R.id.imgloceddit:
                PagerFragment pagerFragment1 = new PagerFragment();
                Map<String, Serializable> parameters = new HashMap<String, Serializable>(2);
                fullShopDetailBean.setEditPage(1);
                parameters.put("FULLDETAIL", fullShopDetailBean);
                getMyActivity().showFragment(pagerFragment1, parameters);
                break;

            case R.id.imgshopddit:
                PagerFragment pagerFragment2 = new PagerFragment();
                Map<String, Serializable> parameters2 = new HashMap<String, Serializable>(2);
                fullShopDetailBean.setEditPage(2);
                parameters2.put("FULLDETAIL", fullShopDetailBean);
                getMyActivity().showFragment(pagerFragment2, parameters2);
                break;
            case R.id.imgrentddit:
                PagerFragment pagerFragment3 = new PagerFragment();
                Map<String, Serializable> parameters3 = new HashMap<String, Serializable>(2);
                fullShopDetailBean.setEditPage(3);
                parameters3.put("FULLDETAIL", fullShopDetailBean);
                getMyActivity().showFragment(pagerFragment3, parameters3);
                break;


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PHOTO_LEFT || requestCode == REQUEST_PHOTO_RIGHT || requestCode == REQUEST_PHOTO_FRO || requestCode == REQUEST_PHOTO_OPP) {
            setVehicleImage(data, requestCode);
        }
    }


    private void setVehicleImage(Intent data, int requestCode) {

        Bitmap bitmap = null;
        try {

            if (data != null) {
                if (data.getExtras() != null) {

                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Bitmap bt=Bitmap.createScaledBitmap(bitmap, 720, 1100, false);
                    Bitmap bt = BITMAP_RESIZER(bitmap, 720, 1100);
                    bt.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] vehicleImage = stream.toByteArray();
                    Uri uri = CommonUtils.getImageUri(getMyActivity(), bt);
                    String encodedImage = Base64.encodeToString(vehicleImage, Base64.DEFAULT);

                    switch (requestCode) {
                        case 101:
                            CustomerFiles customerFiles1 = new CustomerFiles();
                            //customerFiles1.setImagePath(outputFile.getPath());
                            if (fullShopDetailBean.getShopImages().get(0).getImageId() > 0) {
                                customerFiles1.setImage_type(fullShopDetailBean.getShopImages().get(0).getImage_type());
                            } else {
                                customerFiles1.setImage_type(IConstants.LEFT_IMAGE);
                            }
                            customerFiles1.setImage(encodedImage);
                            if (fullShopDetailBean.getShopImages().get(0).getImageId() > 0) {
                                customerFiles1.setImageId(fullShopDetailBean.getShopImages().get(0).getImageId());
                            } else {
                                customerFiles1.setImageId(0);
                            }
                            dataT.add(customerFiles1);

                            break;
                        case 102:
                            CustomerFiles customerFiles2 = new CustomerFiles();
                            if (fullShopDetailBean.getShopImages().get(1).getImageId() > 0) {
                                customerFiles2.setImage_type(fullShopDetailBean.getShopImages().get(1).getImage_type());
                            } else {
                                customerFiles2.setImage_type(IConstants.RIGHT_IMAGE);
                            }
                            customerFiles2.setImage(encodedImage);
                            if (fullShopDetailBean.getShopImages().get(1).getImageId() > 0) {
                                customerFiles2.setImageId(fullShopDetailBean.getShopImages().get(1).getImageId());
                            } else {
                                customerFiles2.setImageId(0);
                            }
                            dataT.add(customerFiles2);

                            break;
                        case 103:
                            CustomerFiles customerFiles3 = new CustomerFiles();
                            if (fullShopDetailBean.getShopImages().get(2).getImageId() > 0) {
                                customerFiles3.setImage_type(fullShopDetailBean.getShopImages().get(2).getImage_type());
                            } else {
                                customerFiles3.setImage_type(IConstants.FRONT_IMAGE);
                            }
                            customerFiles3.setImage(encodedImage);
                            if (fullShopDetailBean.getShopImages().get(2).getImageId() > 0) {
                                customerFiles3.setImageId(fullShopDetailBean.getShopImages().get(2).getImageId());
                            } else {
                                customerFiles3.setImageId(0);
                            }
                            dataT.add(customerFiles3);

                            break;
                        case 104:
                            CustomerFiles customerFiles4 = new CustomerFiles();
                            if (fullShopDetailBean.getShopImages().get(3).getImageId() > 0) {
                                customerFiles4.setImage_type(fullShopDetailBean.getShopImages().get(3).getImage_type());
                            } else {
                                customerFiles4.setImage_type(IConstants.OPPOSITE_IMAGE);
                            }
                            customerFiles4.setImage(encodedImage);
                            if (fullShopDetailBean.getShopImages().get(3).getImageId() > 0) {
                                customerFiles4.setImageId(fullShopDetailBean.getShopImages().get(3).getImageId());
                            } else {
                                customerFiles4.setImageId(0);
                            }
                            dataT.add(customerFiles4);

                            break;

                    }


                    bindLocation();
                    if (checkLocation()) {
                        save();
                    }


                } else {
                    Toast.makeText(getMyActivity(), "Please select image", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getMyActivity(), "Please select image", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getMyActivity(), "Please select image", Toast.LENGTH_SHORT).show();
        }
    }


    private void save() {

        CustomerFiles customerFiles = new CustomerFiles();

        customerFiles = dataT.get(sentCount);

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(IJson.image_string, "" + customerFiles.getImage());
        hashMap.put(IJson.imageType, "" + customerFiles.getImage_type());
        hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());
        hashMap.put(IJson.latitude, "" + getMyActivity().getLocation().getLatitude());
        hashMap.put(IJson.longitude, "" + getMyActivity().getLocation().getLongitude());
        hashMap.put(IJson.imageId, "" + customerFiles.getImageId());


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_PHOTOS, hashMap, new VolleyResponseListener<CustomerFiles>() {
            @Override
            public void onResponse(CustomerFiles[] object) {


                if (object[0] instanceof CustomerFiles) {
                    for (CustomerFiles bean : object) {

                        sentCount++;

                        if (sentCount < dataT.size()) {
                            save();
                        } else {

                            getSuccessDialog("!Congrats", "Shop Photos Saved Successfully", new CustomDialogListener() {
                                @Override
                                public void onResponse() {
                                    getFullDetails();
                                }
                            });


                            return;
                        }

                    }
                }


            }

            @Override
            public void onError(String message) {

            }
        }, CustomerFiles[].class);


    }


    private void bindLocation() {
        customerFiles.setLatitude("" + getMyActivity().getLocation().getLatitude());
        customerFiles.setLongitude("" + getMyActivity().getLocation().getLongitude());
    }

    private boolean checkLocation() {
        if (getMyActivity().getLocation().getLatitude() <= 0 || getMyActivity().getLocation().getLongitude() <= 0) {
            getMyActivity().LocationDialog();
            CommonUtils.showToast(getMyActivity(), "Start GPS Location First");
            return false;
        }

        return true;
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
