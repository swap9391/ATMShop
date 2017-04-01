package com.atpshop.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.atpshop.MainActivity;
import com.atpshop.R;
import com.atpshop.common.CommonUtils;
import com.atpshop.common.FloatingActionButton;
import com.atpshop.common.MarshMallowPermission;
import com.atpshop.constant.CallWebservice;
import com.atpshop.constant.CustomDialogListener;
import com.atpshop.constant.IConstants;
import com.atpshop.constant.IJson;
import com.atpshop.constant.IUrls;
import com.atpshop.constant.VolleyResponseListener;
import com.atpshop.interf.DialogResult;
import com.atpshop.model.CustomerFiles;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

                    GuideDialogFragment dialogFragment = new GuideDialogFragment(new DialogResult() {
                        @Override
                        public void onResult(boolean flag) {
                            if (flag == true) {



                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.TITLE, "LEFT");
                                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri = getMyActivity().getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, REQUEST_PHOTO_LEFT);

                            }
                        }
                    }, IConstants.LEFT_IMAGE
                    );
                    dialogFragment.show(getFragmentManager(), "Dialog Fragment");

                    break;
                case R.id.btnRight:
                case R.id.imgRight:


                    GuideDialogFragment dialogFragment1 = new GuideDialogFragment(new DialogResult() {
                        @Override
                        public void onResult(boolean flag) {
                            if (flag == true) {



                                if (getMyActivity().getLeftId() > 0) {
                                    btnOpposit.setVisibility(View.VISIBLE);
                                    imgOpposit.setVisibility(View.GONE);
                                }
                                ContentValues values1 = new ContentValues();
                                values1.put(MediaStore.Images.Media.TITLE, "RIGHT");
                                values1.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri = getMyActivity().getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values1);
                                Intent cameraIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(cameraIntent2, REQUEST_PHOTO_RIGHT);

                            }
                        }
                    }, IConstants.RIGHT_IMAGE
                    );
                    dialogFragment1.show(getFragmentManager(), "Dialog Fragment");



                    break;

                case R.id.btnFront:
                case R.id.imgFront:

                    GuideDialogFragment dialogFragment2 = new GuideDialogFragment(new DialogResult() {
                        @Override
                        public void onResult(boolean flag) {
                            if (flag == true) {
                                ContentValues values2 = new ContentValues();
                                values2.put(MediaStore.Images.Media.TITLE, "Front");
                                values2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri = getMyActivity().getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values2);
                                Intent cameraIntent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent3.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(cameraIntent3, REQUEST_PHOTO_FRO);

                            }
                        }
                    }, IConstants.FRONT_IMAGE
                    );
                    dialogFragment2.show(getFragmentManager(), "Dialog Fragment");
                    break;

                case R.id.btnOpposite:
                case R.id.imgOpposite:

                    GuideDialogFragment dialogFragment3 = new GuideDialogFragment(new DialogResult() {
                        @Override
                        public void onResult(boolean flag) {
                            if (flag == true) {
                                ContentValues values3 = new ContentValues();
                                values3.put(MediaStore.Images.Media.TITLE, "Opposite");
                                values3.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri = getMyActivity().getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values3);
                                Intent cameraIntent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent4.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(cameraIntent4, REQUEST_PHOTO_OPP);

                            }
                        }
                    }, IConstants.OPPOSITE_IMAGE
                    );
                    dialogFragment3.show(getFragmentManager(), "Dialog Fragment");
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

                    ContentResolver cr = getMyActivity().getContentResolver();
                  /*  thumbnails = MediaStore.Images.Media.getBitmap(
                            getMyActivity().getContentResolver(), imageUri);
*/
                    //  thumbnails= new MediaStore.Images.Media(getMyActivity().getContentResolver(), imageUri);
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
                bt.compress(Bitmap.CompressFormat.PNG, 30, stream);
                byte[] vehicleImage = stream.toByteArray();
                Uri uri = CommonUtils.getImageUri(getMyActivity(), bt);
                String encodedImage = Base64.encodeToString(vehicleImage, Base64.DEFAULT);


                switch (requestCode) {
                    case 101:
                        CustomerFiles customerFiles1 = new CustomerFiles();
                        //customerFiles1.setImagePath(outputFile.getPath());
                        customerFiles1.setImage_type(IConstants.LEFT_IMAGE);
                        customerFiles1.setImage(encodedImage);
                        customerFiles1.setImageId(getMyActivity().getLeftId());
                        dataT.add(customerFiles1);
                        imgLeft.setVisibility(View.VISIBLE);
                        Picasso.with(getMyActivity())
                                .load(uri)
                                .placeholder(R.drawable.circular_progress_dialog)
                                .error(R.drawable.circular_progress_dialog)
                                .into(imgLeft);
                        btnLeft.setVisibility(View.GONE);
                        break;
                    case 102:
                        CustomerFiles customerFiles2 = new CustomerFiles();
                        customerFiles2.setImage_type(IConstants.RIGHT_IMAGE);
                        customerFiles2.setImage(encodedImage);
                        customerFiles2.setImageId(getMyActivity().getRightId());
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
                        customerFiles3.setImageId(getMyActivity().getFrontId());
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
                        customerFiles4.setImageId(getMyActivity().getOppId());
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
        hashMap.put(IJson.imageId, "" + customerFiles.getImageId());
        hashMap.put(IJson.image_string, "" + customerFiles.getImage());
        hashMap.put(IJson.imageType, "" + customerFiles.getImage_type());
        //hashMap.put(IJson.shopId, "1" );
        hashMap.put(IJson.shopId, "" + getMyActivity().getShopId());
        hashMap.put(IJson.latitude, "" + getMyActivity().getLocation().getLatitude());
        hashMap.put(IJson.longitude, "" + getMyActivity().getLocation().getLongitude());


        CallWebservice.getWebservice(getMyActivity(), Request.Method.POST, IUrls.URL_SHOP_PHOTOS, hashMap, new VolleyResponseListener<CustomerFiles>() {
            @Override
            public void onResponse(CustomerFiles[] object) {


                if (object[0] instanceof CustomerFiles) {
                    for (CustomerFiles bean : object) {
                        switch (sentCount) {
                            case 0:
                                getMyActivity().setLeftId(bean.getImageId());
                                break;
                            case 1:
                                getMyActivity().setRightId(bean.getImageId());
                                break;
                            case 2:
                                getMyActivity().setFrontId(bean.getImageId());
                                break;
                        }

                        sentCount++;

                        if (sentCount < dataT.size()) {
                            CommonUtils.showToast(getMyActivity(), "Image" + sentCount + " Uploaded");
                            save();
                        } else {
                            getMyActivity().setOppId(bean.getImageId());
                            dataT = new ArrayList<CustomerFiles>();
                            sentCount = 0;
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


        if (dataT.size() < 4) {
            if (getMyActivity().getLeftId() > 0 ||
                    getMyActivity().getRightId() > 0 ||
                    getMyActivity().getFrontId() > 0 ||
                    getMyActivity().getRightId() > 0) {
                int remain = 4 - dataT.size();
                CommonUtils.showToast(getMyActivity(), "Re Capture " + remain + " Photos");
            }
            return false;
        }

        if (getMyActivity().getShopId() <= 0) {
            CommonUtils.showToast(getMyActivity(), "Please Fill Shop Location Detail First");
            return false;
        }
        return true;
    }


}


