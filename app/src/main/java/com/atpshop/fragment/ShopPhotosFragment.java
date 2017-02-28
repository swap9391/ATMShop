package com.atpshop.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.common.GPSTracker;
import com.atpshop.common.MarshMallowPermission;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.model.CustomerFiles;
import com.atpshop.model.OwnerDetailBean;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.atpshop.constant.IConstants.USER_ID;

/**
 * Created by root on 11/1/17.
 */

public class ShopPhotosFragment extends Fragment implements View.OnClickListener {


    //scroll image
    List<String> imgArray;
    Uri fileView;
    int imageCount = 0, sentCount = 0;
    File file;
    List<CustomerFiles> dataT;
    Button btnLeft, btnRight, btnFront, btnOpposit;
    ImageView imgLeft, imgRight, imgFront, imgOpposit;
    ProgressBar progressBar;
    TextView txtPercentage;
    private int REQUEST_PHOTO_LEFT = 101, REQUEST_PHOTO_RIGHT = 102, REQUEST_PHOTO_FRO = 103, REQUEST_PHOTO_OPP = 104;

    CustomerFiles customerFiles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.add_shop_photos, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txtPercentage = (TextView) view.findViewById(R.id.txtPercentage);
        btnLeft = (Button) view.findViewById(R.id.btnLeft);
        btnRight = (Button) view.findViewById(R.id.btnRight);
        btnFront = (Button) view.findViewById(R.id.btnFront);
        btnOpposit = (Button) view.findViewById(R.id.btnOpposite);

        imgLeft = (ImageView) view.findViewById(R.id.imgLeft);
        imgRight = (ImageView) view.findViewById(R.id.imgRight);
        imgFront = (ImageView) view.findViewById(R.id.imgFront);
        imgOpposit = (ImageView) view.findViewById(R.id.imgOpposite);

        customerFiles = new CustomerFiles();
        if (getMyActivity().getLocation().getLatitude() <= 0 || getMyActivity().getLocation().getLongitude() <= 0) {
            getMyActivity().LocationDialog();
        }


        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnFront.setOnClickListener(this);
        btnOpposit.setOnClickListener(this);

        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        imgFront.setOnClickListener(this);
        imgOpposit.setOnClickListener(this);


        dataT = new ArrayList<CustomerFiles>();
        imgArray = new ArrayList<>();


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


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLeft:
            case R.id.imgLeft:
                Intent cameraIntent1 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                getMyActivity().startActivityForResult(cameraIntent1, REQUEST_PHOTO_LEFT);
                break;
            case R.id.btnRight:
            case R.id.imgRight:
                Intent cameraIntent2 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                getMyActivity().startActivityForResult(cameraIntent2, REQUEST_PHOTO_RIGHT);
                break;

            case R.id.btnFront:
            case R.id.imgFront:
                Intent cameraIntent3 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                getMyActivity().startActivityForResult(cameraIntent3, REQUEST_PHOTO_FRO);
                break;

            case R.id.btnOpposite:
            case R.id.imgOpposite:
                Intent cameraIntent4 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                getMyActivity().startActivityForResult(cameraIntent4, REQUEST_PHOTO_OPP);
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
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] vehicleImage = stream.toByteArray();
                    Uri uri = CommonUtils.getImageUri(getMyActivity(),bitmap);
                    String encodedImage = Base64.encodeToString(vehicleImage, Base64.DEFAULT);

                   // File outputFile = savebitmap(bitmap);

                   // stream.writeTo(new FileOutputStream(outputFile));
                    //    addTowedVehiRequest.setVehicleImage(Base64.encodeToString(vehicleImage, Base64.DEFAULT));

                    // bitmap = Bitmap.createScaledBitmap(bitmap, 50, 40, true);
                    // capturedImg.setVisibility(View.VISIBLE);
                    //capturedImg.setImageBitmap(bitmap);


                    switch (requestCode) {
                        case 101:
                            CustomerFiles customerFiles1 = new CustomerFiles();
                            //customerFiles1.setImagePath(outputFile.getPath());
                            customerFiles1.setImage_type(IConstants.LEFT_IMAGE);
                            customerFiles1.setImage(encodedImage);
                            dataT.add(customerFiles1);
                            imgLeft.setVisibility(View.VISIBLE);
                            Picasso.with(getMyActivity())
                                    .load(uri)
                                    .placeholder(R.drawable.circular_progress_dialog)
                                    .error(R.drawable.circular_progress_dialog)         // optional
                                    .into(imgLeft);
                            btnLeft.setVisibility(View.GONE);
                            break;
                        case 102:
                            CustomerFiles customerFiles2 = new CustomerFiles();
                            customerFiles2.setImage_type(IConstants.RIGHT_IMAGE);
                            customerFiles2.setImage(encodedImage);
                            dataT.add(customerFiles2);
                            imgRight.setVisibility(View.VISIBLE);
                            Picasso.with(getMyActivity())
                                    .load(uri)
                                    .placeholder(R.drawable.circular_progress_dialog)
                                    .error(R.drawable.circular_progress_dialog)         // optional
                                    .into(imgRight);
                            btnRight.setVisibility(View.GONE);
                            break;
                        case 103:
                            CustomerFiles customerFiles3 = new CustomerFiles();
                            customerFiles3.setImage_type(IConstants.FRONT_IMAGE);
                            customerFiles3.setImage(encodedImage);
                            dataT.add(customerFiles3);
                            imgFront.setVisibility(View.VISIBLE);
                            Picasso.with(getMyActivity())
                                    .load(uri)
                                    .placeholder(R.drawable.circular_progress_dialog)
                                    .error(R.drawable.circular_progress_dialog)         // optional
                                    .into(imgFront);
                            btnFront.setVisibility(View.GONE);
                            break;
                        case 104:
                            CustomerFiles customerFiles4 = new CustomerFiles();
                            customerFiles4.setImage_type(IConstants.OPPOSITE_IMAGE);
                            customerFiles4.setImage(encodedImage);
                            dataT.add(customerFiles4);
                            imgOpposit.setVisibility(View.VISIBLE);
                            Picasso.with(getMyActivity())
                                    .load(uri)
                                    .placeholder(R.drawable.circular_progress_dialog)
                                    .error(R.drawable.circular_progress_dialog)         // optional
                                    .into(imgOpposit);
                            btnOpposit.setVisibility(View.GONE);

                            break;

                    }

                    imgArray.add(Base64.encodeToString(vehicleImage, Base64.DEFAULT));

                    // addTowedVehiRequest.setImgArray(imgArray);

                    imageCount++;

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

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        String fileName = null;
        StringBuffer stringBuffer = new StringBuffer();
        fileName = stringBuffer.append("ATM" + imageCount)
                .append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).append(".jpg")
                .toString();
        File file = new File(extStorageDirectory, fileName);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, fileName);

        }

        try {
            outStream = new FileOutputStream(file);
          //  bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }


    /**
     * Uploading the file to server
     */
    private void save() {

        CustomerFiles customerFiles = new CustomerFiles();

        customerFiles = dataT.get(sentCount);

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(IJson.image_string, "" + customerFiles.getImage());
        hashMap.put(IJson.imageType, "" + customerFiles.getImage_type());
        //hashMap.put(IJson.shopId, "1" );
        hashMap.put(IJson.shopId,""+ getMyActivity().getShopId());
        hashMap.put(IJson.latitude,""+ getMyActivity().getLocation().getLatitude());
        hashMap.put(IJson.longitude,""+ getMyActivity().getLocation().getLongitude() );
        hashMap.put(IJson.imageId,"0" );



        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_PHOTOS, hashMap, new VolleyResponseListener<CustomerFiles>() {
            @Override
            public void onResponse(CustomerFiles[] object) {


                if (object[0] instanceof CustomerFiles) {
                    for (CustomerFiles bean : object) {

                        sentCount++;

                        if (sentCount < dataT.size()) {
                            save();
                        } else {
                            PagerFragment pager = ((PagerFragment) getParentFragment());
                            pager.setPage(5);
                            return;
                        }

                        CommonUtils.showToast(getMyActivity(), "Owner Data Saved Successfully");
                        getMyActivity().setOwnerId(bean.getShopId());
                    }
                }


            }

            @Override
            public void onError(String message) {
                CommonUtils.showToast(getMyActivity(), message);
            }
        }, CustomerFiles[].class);


    }


    private void bindModel() {
        customerFiles.setLatitude("" + getMyActivity().getLocation().getLatitude());
        customerFiles.setLongitude("" + getMyActivity().getLocation().getLongitude());
    }

    private boolean check() {
        if (getMyActivity().getLocation().getLatitude() <= 0 || getMyActivity().getLocation().getLongitude() <= 0) {
            getMyActivity().LocationDialog();
            CommonUtils.showToast(getMyActivity(), "Start GPS Location First");
            return false;
        }

        return true;
    }


}


