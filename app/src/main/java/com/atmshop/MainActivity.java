package com.atmshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atmshop.activity.LoginActivity;
import com.atmshop.common.CommonUtils;
import com.atmshop.common.GPSTracker;
import com.atmshop.common.MarshMallowPermission;
import com.atmshop.constant.IConstants;
import com.atmshop.fragment.AboutFragment;
import com.atmshop.fragment.ChangePasswordFrag;
import com.atmshop.fragment.PagerFragment;
import com.atmshop.fragment.ShopListFragment;
import com.atmshop.fragment.TermsFragment;
import com.atmshop.listners.GpsLocationReceiver;
import com.atmshop.model.FullShopDetailBean;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {

    private ViewPager viewPager;
    Toolbar toolbar;
    int position;
    private int REQUEST_PHOTO_LEFT = 101, REQUEST_PHOTO_RIGHT = 102, REQUEST_PHOTO_FRO = 103, REQUEST_PHOTO_OPP = 104;
    int ownerId = 0, shopId = 0, questionId = 0, rentId = 0;
    int leftId = 0, rightId = 0, frontId = 0, oppId = 0;
    FullShopDetailBean fullShopDetailBean;
    //Drawer
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_SHOP_DETLS = "shopdtls";
    private static final String TAG_CHANGE_PASS = "changepass";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    //Location
    //location
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 10;
    GPSTracker gpsTracker;
    MarshMallowPermission marshMallowPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        marshMallowPermission = new MarshMallowPermission(this);

        if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage() && !marshMallowPermission.checkPermissionForReadExternalStorage()) {
            marshMallowPermission.requestPermissionForCamera();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        if (getLocation().getLatitude() <= 0 || getLocation().getLongitude() <= 0) {
            LocationDialog();
        }

        fullShopDetailBean = new FullShopDetailBean();
        showFragment(PagerFragment.class);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        mHandler = new Handler();
        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        registerReceiver(receiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));

    }

    private GpsLocationReceiver receiver = new GpsLocationReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("Location", "changed");
        }
    };

    private void loadNavHeader() {
        // name, website
        txtName.setText(CommonUtils.getSharedPref(IConstants.USER_NAME, MainActivity.this));

        txtWebsite.setText(CommonUtils.getSharedPref(IConstants.USER_MOBILE, MainActivity.this));

        // loading header background image

        // showing dot next to notifications label
        // navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }


    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();


        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            // show or hide the fab button
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                PagerFragment homeFragment = new PagerFragment();
                return homeFragment;
            case 1:
                // photos
                ShopListFragment moviesFragment = new ShopListFragment();
                return moviesFragment;
            case 2:
                // photos
                ChangePasswordFrag changepass = new ChangePasswordFrag();
                return changepass;

            default:
                return new PagerFragment();
        }
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_shop_dtls:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_SHOP_DETLS;
                        break;
                    case R.id.nav_change_pass:
                        showFragment(ChangePasswordFrag.class);
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        showFragment(AboutFragment.class);
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        showFragment(TermsFragment.class);
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_share:
                        // Share App
                        try {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, "ATM Shop");
                            String sAux = "\nLet me recommend you this application\n\n";
                            sAux = sAux + "https://play.google.com/store/apps/details?id=com.atmshop&hl=en \n\n";
                            i.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(i, "choose one"));
                        } catch (Exception e) {
                            //e.toString();
                        }
                        return true;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    /*// show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }
*/


    public void setTitle(String title) {
        toolbar.setTitle(title);
    }


    public void showFragment(Class<? extends Fragment> fragmentClass) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, Fragment.instantiate(this, fragmentClass.getCanonicalName()));
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_PHOTO_LEFT || requestCode == REQUEST_PHOTO_RIGHT || requestCode == REQUEST_PHOTO_FRO || requestCode == REQUEST_PHOTO_OPP) {

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                fragment.onActivityResult(requestCode, requestCode, data);
            }
            /*if (requestCode == REQUEST_CHECK_SETTINGS) {
                if (resultCode == RESULT_OK) {
                    showToast("GPS enabled");
                } else {
                    showToast("GPS is not enabled");
                }
            } else if (requestCode == METER_IMAGE_REQUEST) {
                setMeterImage(data, requestCode);
            }
*/

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getLeftId() {
        return leftId;
    }

    public void setLeftId(int leftId) {
        this.leftId = leftId;
    }

    public int getRightId() {
        return rightId;
    }

    public void setRightId(int rightId) {
        this.rightId = rightId;
    }

    public int getFrontId() {
        return frontId;
    }

    public void setFrontId(int frontId) {
        this.frontId = frontId;
    }

    public int getOppId() {
        return oppId;
    }

    public void setOppId(int oppId) {
        this.oppId = oppId;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }
    //Location


    public void LocationDialog() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                // NO need to show the dialog;
                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                // Location settings are not satisfied. Show the user a dialog
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and
                    // check the result
                    // in onActivityResult().

                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {

                    // failed to show
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any
                // dialog now
                break;
        }
    }

    public GPSTracker getLocation() {
        gpsTracker = new GPSTracker(this);
        // check if GPS enabled
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);

        }
        return gpsTracker;
    }

    public void showFragment(Fragment fragment, Map<String, Serializable> parameters) {
        Bundle bundle = new Bundle();
        if (parameters != null) {
            Set fragmentTransaction = parameters.entrySet();
            Iterator iterator = fragmentTransaction.iterator();

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                bundle.putSerializable((String) entry.getKey(), (Serializable) entry.getValue());
            }
        }

        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction1 = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.frame, fragment);
        fragmentTransaction1.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                LogoutDialog();
                break;
        }
        return true;
    }

    public static AlertDialog.Builder showAlertDialog(Context context) {
        AlertDialog.Builder alertDialog = null;
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Do you want to logout?");
        return alertDialog;
    }

    private void LogoutDialog() {

        try {
            AlertDialog.Builder builder = showAlertDialog(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    CommonUtils.removeSharePref(IConstants.USER_ID, MainActivity.this);
                    CommonUtils.removeSharePref(IConstants.USER_NAME, MainActivity.this);
                    CommonUtils.removeSharePref(IConstants.USER_MOBILE, MainActivity.this);


                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).show();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public FullShopDetailBean getFullShopDetailBean() {
        return fullShopDetailBean;
    }

    public void setFullShopDetailBean(FullShopDetailBean fullShopDetailBean) {
        this.fullShopDetailBean = fullShopDetailBean;
    }

    public void refresh() {
        setOwnerId(0);
        setShopId(0);
        setQuestionId(0);
        setLeftId(0);
        setRightId(0);
        setFrontId(0);
        setOppId(0);
    }

    public MarshMallowPermission getMarshMallowPermission() {
        return marshMallowPermission;
    }

    public void setMarshMallowPermission(MarshMallowPermission marshMallowPermission) {
        this.marshMallowPermission = marshMallowPermission;
    }


}
