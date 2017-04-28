package com.atmshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.atmshop.MainActivity;
import com.atmshop.R;
import com.atmshop.model.FullShopDetailBean;
import com.filippudak.ProgressPieView.ProgressPieView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/1/17.
 */

public class PagerFragment extends CommonFragment implements View.OnClickListener {
    private LinearLayout dotsLayout;
    private ImageButton[] dots;
    View view;

    private ProgressPieView mProgressPieView;
    Handler handler;
    int status = 0;
    int currentstatus = 0;
    boolean progressflag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_view_pager, container, false);
        getMyActivity().getToolbar().setTitle("ATM Shop");
        dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);
        viewPager = (CustomViewPager) view.findViewById(R.id.pager);

        setupViewPager(viewPager);
        final View touchView = viewPager;
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //addBottomDots(0);
        ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_complete_one);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                pageSelected(position);
                //addBottomDots(position);



                /*// changing the next button text 'NEXT' / 'GOT IT'
                if (position == layouts.length - 1) {
                    // last page. make button text to GOT IT
                    btnNext.setText(getString(R.string.start));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    // still pages are left
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Default version
        mProgressPieView = (ProgressPieView) view.findViewById(R.id.progressPieView);

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

        if (getSerializer("FULLDETAIL", FullShopDetailBean.class) != null) {
            mProgressPieView.setVisibility(View.GONE);
            getMyActivity().setFullShopDetailBean(getSerializer("FULLDETAIL", FullShopDetailBean.class));
            setPage(getMyActivity().getFullShopDetailBean().getEditPage());
            progressflag = false;
            pageSelected(getMyActivity().getFullShopDetailBean().getEditPage());
            disablePager();

        } else {
            mProgressPieView.setVisibility(View.VISIBLE);
            progressflag = true;

        }


        return view;

    }

    public void pageSelected(int position) {
        switch (position) {
            case 0:
                getMyActivity().setTitle("Owner Details");
                ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_complete_one);
                ((ImageButton) view.findViewById(R.id.navigateTwo)).setImageResource(R.mipmap.ic_uncomplete_two);
                ((ImageButton) view.findViewById(R.id.navigateThree)).setImageResource(R.mipmap.ic_uncomplete_three);
                ((ImageButton) view.findViewById(R.id.navigateFour)).setImageResource(R.mipmap.ic_uncomplete_four);
                ((ImageButton) view.findViewById(R.id.navigateFive)).setImageResource(R.mipmap.ic_uncomplete_five);
                ((ImageButton) view.findViewById(R.id.navigateSix)).setImageResource(R.mipmap.ic_uncomplete_six);
                mProgressPieView.setVisibility(View.VISIBLE);
                if (progressflag == true) {
                    mProgressPieView.setProgress(status);
                    mProgressPieView.setText(status + "%");
                }
                break;
            case 1:
                getMyActivity().setTitle("Shop Location Details");
                ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_uncomplete_one);
                ((ImageButton) view.findViewById(R.id.navigateTwo)).setImageResource(R.mipmap.ic_complete_two);
                ((ImageButton) view.findViewById(R.id.navigateThree)).setImageResource(R.mipmap.ic_uncomplete_three);
                ((ImageButton) view.findViewById(R.id.navigateFour)).setImageResource(R.mipmap.ic_uncomplete_four);
                ((ImageButton) view.findViewById(R.id.navigateFive)).setImageResource(R.mipmap.ic_uncomplete_five);
                ((ImageButton) view.findViewById(R.id.navigateSix)).setImageResource(R.mipmap.ic_uncomplete_six);
                status = 0;
                mProgressPieView.setVisibility(View.VISIBLE);
                if (progressflag == true) {
                    if (currentstatus <= 30) {
                        currentstatus = 30;
                    }
                    ShowProgressDialog(currentstatus);
                }
                break;

            case 2:
                getMyActivity().setTitle("Shop Details");
                ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_uncomplete_one);
                ((ImageButton) view.findViewById(R.id.navigateTwo)).setImageResource(R.mipmap.ic_uncomplete_two);
                ((ImageButton) view.findViewById(R.id.navigateThree)).setImageResource(R.mipmap.ic_complete_three);
                ((ImageButton) view.findViewById(R.id.navigateFour)).setImageResource(R.mipmap.ic_uncomplete_four);
                ((ImageButton) view.findViewById(R.id.navigateFive)).setImageResource(R.mipmap.ic_uncomplete_five);
                ((ImageButton) view.findViewById(R.id.navigateSix)).setImageResource(R.mipmap.ic_uncomplete_six);
                status = 30;
                mProgressPieView.setVisibility(View.VISIBLE);
                if (progressflag == true) {
                    if (currentstatus <= 45) {
                        currentstatus = 45;
                    }
                    ShowProgressDialog(currentstatus);
                }
                break;


            case 3:
                getMyActivity().setTitle("Shop Rent");
                ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_uncomplete_one);
                ((ImageButton) view.findViewById(R.id.navigateTwo)).setImageResource(R.mipmap.ic_uncomplete_two);
                ((ImageButton) view.findViewById(R.id.navigateThree)).setImageResource(R.mipmap.ic_uncomplete_three);
                ((ImageButton) view.findViewById(R.id.navigateFour)).setImageResource(R.mipmap.ic_complete_four);
                ((ImageButton) view.findViewById(R.id.navigateFive)).setImageResource(R.mipmap.ic_uncomplete_five);
                ((ImageButton) view.findViewById(R.id.navigateSix)).setImageResource(R.mipmap.ic_uncomplete_six);
                status = 45;
                mProgressPieView.setVisibility(View.VISIBLE);
                if (progressflag == true) {
                    if (currentstatus <= 60) {
                        currentstatus = 60;
                    }
                    ShowProgressDialog(currentstatus);
                }
                break;


            case 4:
                getMyActivity().setTitle("Shop Photos");
                ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_uncomplete_one);
                ((ImageButton) view.findViewById(R.id.navigateTwo)).setImageResource(R.mipmap.ic_uncomplete_two);
                ((ImageButton) view.findViewById(R.id.navigateThree)).setImageResource(R.mipmap.ic_uncomplete_three);
                ((ImageButton) view.findViewById(R.id.navigateFour)).setImageResource(R.mipmap.ic_uncomplete_four);
                ((ImageButton) view.findViewById(R.id.navigateFive)).setImageResource(R.mipmap.ic_complete_five);
                ((ImageButton) view.findViewById(R.id.navigateSix)).setImageResource(R.mipmap.ic_uncomplete_six);
                mProgressPieView.setVisibility(View.VISIBLE);
                status = 60;
                if (progressflag == true) {
                    if (currentstatus <= 85) {
                        currentstatus = 85;
                    }
                    ShowProgressDialog(currentstatus);
                    progressflag = true;
                }
                break;

            case 5:
                getMyActivity().setTitle("Questioneries");
                ((ImageButton) view.findViewById(R.id.navigateOne)).setImageResource(R.mipmap.ic_uncomplete_one);
                ((ImageButton) view.findViewById(R.id.navigateTwo)).setImageResource(R.mipmap.ic_uncomplete_two);
                ((ImageButton) view.findViewById(R.id.navigateThree)).setImageResource(R.mipmap.ic_uncomplete_three);
                ((ImageButton) view.findViewById(R.id.navigateFour)).setImageResource(R.mipmap.ic_uncomplete_four);
                ((ImageButton) view.findViewById(R.id.navigateFive)).setImageResource(R.mipmap.ic_uncomplete_five);
                ((ImageButton) view.findViewById(R.id.navigateSix)).setImageResource(R.mipmap.ic_complete_six);
                mProgressPieView.setVisibility(View.GONE);
                break;


        }

    }

    public void disablePager() {
        viewPager.setPagingEnabled(false);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OwnerDetailFragment(), "ONE");
        adapter.addFragment(new ShopLocationFragment(), "TWO");
        adapter.addFragment(new ShopDetailFragment(), "THREE");
        adapter.addFragment(new RentFragment(), "FOUR");
        adapter.addFragment(new ShopPhotosFragment(), "FIVE");
        adapter.addFragment(new QuestioneriesFragment(), "SIX");
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }

    public void next() {
        int current = getItem(+1);
        //  if (current < layouts.length) {
        // move to next screen
        viewPager.setCurrentItem(current);
        /*} else {
            launchHomeScreen();
        }*/
    }

    public void setPage(int position) {
        viewPager.setCurrentItem(position);
    }

    public int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();
        private Fragment mCurrentFragment;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            this.enabled = true;
        }

        private boolean enabled;

        public Fragment getCurrentFragment() {

            return mCurrentFragment;
        }


        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            // addBottomDots(getMyActivity().getPosition());
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }

        public void setPagingEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentPagerAdapter fragmentPagerAdapter = (FragmentPagerAdapter) viewPager.getAdapter();
        for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
            Fragment viewPagerFragment = fragmentPagerAdapter.getItem(i);
            if (viewPagerFragment != null) {
                viewPagerFragment.onActivityResult(requestCode, resultCode, data);
                // Do something with your Fragment
                // Check viewPagerFragment.isResumed() if you intend on interacting with any views.
            }
        }


    }

    /*public void addBottomDots(int currentPage) {

        dots = new ImageButton[3];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageButton(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(colorsInactive[2]);
            dotsLayout.addView(dots[i]);
        }

        dots[0].setOnClickListener(this);
        dots[1].setOnClickListener(this);
        dots[2].setOnClickListener(this);

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[0]);
    }
*/
    public MainActivity getMyActivity() {
        return (MainActivity) getActivity();
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
