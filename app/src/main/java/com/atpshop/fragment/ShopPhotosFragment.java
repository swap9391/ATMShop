package com.atpshop.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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
import com.atpshop.constant.CustomDialogListener;
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

import static android.R.attr.data;
import static android.R.attr.path;
import static android.R.attr.thumbnail;
import static com.atpshop.constant.IConstants.USER_ID;

/**
 * Created by root on 11/1/17.
 */

public class ShopPhotosFragment extends CommonFragment implements View.OnClickListener {


    //scroll image
    List<String> imgArray;
    Uri fileView;
    int imageCount = 0, sentCount = 0;
    File file;
    List<CustomerFiles> dataT;
    Button btnLeft, btnRight, btnFront, btnOpposit;
    ImageView imgLeft, imgRight, imgFront, imgOpposit;
    private int REQUEST_PHOTO_LEFT = 101, REQUEST_PHOTO_RIGHT = 102, REQUEST_PHOTO_FRO = 103, REQUEST_PHOTO_OPP = 104;
    CustomerFiles customerFiles;
    File sdImageMainDirectory;
    Uri imageUri;
    Bitmap thumbnails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.add_shop_photos, container, false);
        btnLeft = (Button) view.findViewById(R.id.btnLeft);
        btnRight = (Button) view.findViewById(R.id.btnRight);
        btnFront = (Button) view.findViewById(R.id.btnFront);
        btnOpposit = (Button) view.findViewById(R.id.btnOpposite);

        imgLeft = (ImageView) view.findViewById(R.id.imgLeft);
        imgRight = (ImageView) view.findViewById(R.id.imgRight);
        imgFront = (ImageView) view.findViewById(R.id.imgFront);
        imgOpposit = (ImageView) view.findViewById(R.id.imgOpposite);

        customerFiles = new CustomerFiles();


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


        File root = new File(Environment
                .getExternalStorageDirectory()
                + File.separator + "myDir" + File.separator);
        root.mkdirs();
        sdImageMainDirectory = new File(root, "myPicName");


        return view;

    }


    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {

        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(getActivity());

        if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage() && !marshMallowPermission.checkPermissionForReadExternalStorage()) {
            marshMallowPermission.requestPermissionForCamera();
            marshMallowPermission.requestPermissionForExternalStorage();
            marshMallowPermission.requestPermissionForReadExternalStorage();

        } else {

            switch (v.getId()) {
                case R.id.btnLeft:
                case R.id.imgLeft:

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "LEFT");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getMyActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_PHOTO_LEFT);


                   /* Intent cameraIntent1 = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    getMyActivity().startActivityForResult(cameraIntent1, REQUEST_PHOTO_LEFT);*/
                    break;
                case R.id.btnRight:
                case R.id.imgRight:
                    ContentValues values1 = new ContentValues();
                    values1.put(MediaStore.Images.Media.TITLE, "RIGHT");
                    values1.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getMyActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values1);
                    Intent cameraIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent2, REQUEST_PHOTO_RIGHT);

                    break;

                case R.id.btnFront:
                case R.id.imgFront:
                    ContentValues values2 = new ContentValues();
                    values2.put(MediaStore.Images.Media.TITLE, "Front");
                    values2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getMyActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values2);
                    Intent cameraIntent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent3.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent3, REQUEST_PHOTO_FRO);

                    break;

                case R.id.btnOpposite:
                case R.id.imgOpposite:
                    ContentValues values3 = new ContentValues();
                    values3.put(MediaStore.Images.Media.TITLE, "Opposite");
                    values3.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getMyActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values3);
                    Intent cameraIntent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent4.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent4, REQUEST_PHOTO_OPP);
                    break;


            }
        }
    }


    @Deprecated
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getMyActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PHOTO_LEFT || requestCode == REQUEST_PHOTO_RIGHT || requestCode == REQUEST_PHOTO_FRO || requestCode == REQUEST_PHOTO_OPP) {


            if (resultCode == Activity.RESULT_OK) {
                try {
                    thumbnails = MediaStore.Images.Media.getBitmap(
                            getMyActivity().getContentResolver(), imageUri);

                    String imageurl = getRealPathFromURI(imageUri);
                    setVehicleImage(imageurl, requestCode);
                    Log.e("", imageurl);
                } catch (Exception e) {
                    e.printStackTrace();
                    CommonUtils.showToast(getMyActivity(), "Try Again");
                }

            } else {
                CommonUtils.showToast(getMyActivity(), "Capture Cancelled");
            }


        }
    }

    public Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, paint);

        return scaledBitmap;

    }

    private void setVehicleImage(String uristr, int requestCode) {

        Bitmap bitmap = null;
        try {

            if (uristr != null) {


                bitmap = CommonUtils.getBitmap(uristr);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Bitmap bt=Bitmap.createScaledBitmap(bitmap, 720, 1100, false);
                Bitmap bt = BITMAP_RESIZER(bitmap, 500, 600);
                bt.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] vehicleImage = stream.toByteArray();
                Uri uri = CommonUtils.getImageUri(getMyActivity(), bt);
                String encodedImage = Base64.encodeToString(vehicleImage, Base64.DEFAULT);


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

        } catch (Exception e) {
            Toast.makeText(getMyActivity(), "Please select image", Toast.LENGTH_SHORT).show();
        }
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
        hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());
        hashMap.put(IJson.latitude, "" + getMyActivity().getLocation().getLatitude());
        hashMap.put(IJson.longitude, "" + getMyActivity().getLocation().getLongitude());
        hashMap.put(IJson.imageId, "0");


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_PHOTOS, hashMap, new VolleyResponseListener<CustomerFiles>() {
            @Override
            public void onResponse(CustomerFiles[] object) {


                if (object[0] instanceof CustomerFiles) {
                    for (CustomerFiles bean : object) {

                        sentCount++;

                        if (sentCount < dataT.size()) {
                            CommonUtils.showToast(getMyActivity(), "Image" + sentCount + " Uploaded");
                            save();
                        } else {

                            getSuccessDialog("!Congrats", "Shop Photos Saved Successfully", new CustomDialogListener() {
                                @Override
                                public void onResponse() {

                                    PagerFragment pager = ((PagerFragment) getParentFragment());
                                    pager.setPage(5);

                                }
                            });


                            return;
                        }

                    }
                }


            }

            @Override
            public void onError(String message) {
                getErroDialog(message);
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


        if (getMyActivity().getShopId() <= 0) {
            CommonUtils.showToast(getMyActivity(), "Please Fill Shop Location Detail First");
            return false;
        }

        if (dataT.size() < 4) {
            CommonUtils.showToast(getMyActivity(), "Capture 4 Photos");
            return false;
        }

        return true;
    }


}


