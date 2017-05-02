package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.themallbd.workspaceit.fragment.CheckoutViewFragment;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.fragment.CartFragment;
import com.themallbd.workspaceit.fragment.PaymentFragment;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends BaseActivityWithoutDrawer implements TabLayout.OnTabSelectedListener {


    public static ViewPager mViewPager;
    public static TabLayout tabLayout;
    public static int tabFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mViewPager = (ViewPager) findViewById(R.id.container);


        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(this);


    }


    public void continueShoppinBtnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    public void chekoutBtnClick(View view) {
        tabFlag = 1;
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

    }

    private void setupViewPager(ViewPager mViewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CartFragment(), "Basket");
        adapter.addFragment(new CheckoutViewFragment(), "Checkout");
        adapter.addFragment(new PaymentFragment(), "Payment");
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tabFlag = 0;
        Utility.voucherDiscounts.clear();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        if (tabFlag == 0) {
            if (tab.getPosition() == 0) {
                tabFlag = 0;
                tab = tabLayout.getTabAt(0);
                tab.select();
                mViewPager.setCurrentItem(0);

            } else if (tab.getPosition() == 1 && Utility.shoppingCart.productCell.size() < 1 && Utility.shoppingCart.mallBdPackageCell.size()<1) {
                MakeToast.showToast(this, "Your shopping Cart is empty");
                tab = tabLayout.getTabAt(0);
                tab.select();

            } else if (tab.getPosition() == 1) {
                tabFlag = 1;
                tab = tabLayout.getTabAt(1);
                tab.select();
                mViewPager.setCurrentItem(1);


            } else if (tab.getPosition() == 2) {
                tab = tabLayout.getTabAt(0);
                tab.select();

                MakeToast.showToast(this, "First proceed from checkout Page");
            }


        } else if (tabFlag == 1) {
            if (tab.getPosition() == 0) {
                mViewPager.setCurrentItem(0);
                tabFlag = 0;

            } else if (tab.getPosition() == 1) {
                mViewPager.setCurrentItem(1);
                tabFlag = 1;
            } else if (tab.getPosition() == 2) {

                tab = tabLayout.getTabAt(1);
                tab.select();
                mViewPager.setCurrentItem(1);
                MakeToast.showToast(this, "Please Confirm your shipping information from below...");

            }


        } else if (tabFlag == 2) {
            if (tab.getPosition() == 0) {
                mViewPager.setCurrentItem(0);

            } else if (tab.getPosition() == 1) {
                mViewPager.setCurrentItem(1);

            } else if (tab.getPosition() == 2) {
                mViewPager.setCurrentItem(2);

            }

        }


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
