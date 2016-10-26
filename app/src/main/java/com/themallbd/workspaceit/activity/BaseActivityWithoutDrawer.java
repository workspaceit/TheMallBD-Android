package com.themallbd.workspaceit.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.SearchProductAdapter;
import com.themallbd.workspaceit.asynctask.ProductSearchAsynTask;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class BaseActivityWithoutDrawer extends AppCompatActivity implements SearchView.OnQueryTextListener {


    //public static int mCARTCOUNT = 0;
    private TextView cartTV;

    private Toolbar toolbar;

    private SessionManager sessionManager;
    private SearchView searchView = null;
    private SearchProductAdapter searchProductAdapter;
    SearchView.SearchAutoComplete searchAutoComplete;
    private TextView toolBarTitle;
    private SearchView mSearchView;
    public static boolean otherPageSearchCallFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        searchProductAdapter = new SearchProductAdapter(this, R.layout.search_product_row, Utility.searchProductTitle);

    }


    @Override
    protected void onResume() {
        try {
            super.onResume();
            if (toolbar != null)
                invalidateOptionsMenu();

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        try {
            super.setContentView(layoutResID);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            ImageView imageView= (ImageView) toolbar.findViewById(R.id.toolbar_icon);
           /* imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(BaseActivityWithoutDrawer.this,MainActivity.class);
                    intent.putExtra("toolbar_click",true);
                    startActivity(intent);
                }
            });*/


            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.action_bar_gradient));

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

       try {
           MenuInflater inflater = getMenuInflater();
           inflater.inflate(R.menu.menu_main, menu);
           MenuItem item = menu.findItem(R.id.action_cart);
           MenuItemCompat.setActionView(item, R.layout.cart_update_count);

           MenuItem drawerIcon = menu.findItem(R.id.action_openRight);
           drawerIcon.setVisible(false);


           final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
           SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
           searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


           if (searchView != null) {
               searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
               searchView.setOnQueryTextListener(this);
               searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
               searchAutoComplete.setThreshold(3);

           }


           View view = MenuItemCompat.getActionView(item);
           Button CARTCOUNT = (Button) view.findViewById(R.id.notif_count);
           CARTCOUNT.setText(String.valueOf((Utility.shoppingCart.productCell.size() + Utility.shoppingCart.mallBdPackageCell.size()) + ""));

           CARTCOUNT.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                   startActivity(intent);

               }
           });

       }catch (Exception ex){
           ex.printStackTrace();
       }
        return true;
    }

    public void notifyOnSeacrDataChnaged() {

        if (Utility.searchProductTitle.size() > 0) {
            searchAutoComplete.setAdapter(searchProductAdapter);
            searchProductAdapter.notifyDataSetChanged();
            searchAutoComplete.showDropDown();
        }else {
            searchAutoComplete.setAdapter(searchProductAdapter);
            searchProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.equals("")) {
            MakeToast.showToast(getApplicationContext(), "You didn't type anything...");
            return false;
        }
        Intent intent = new Intent(getApplicationContext(), SearchProductListActivity.class);
        intent.putExtra("keyword", query);
        startActivity(intent);
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("got", newText);
        if (newText.length() > 2 && !newText.equals("") && otherPageSearchCallFlag) {
            otherPageSearchCallFlag = false;
            Utility.searchProductTitle.clear();
            searchProductAdapter.notifyDataSetChanged();
            new ProductSearchAsynTask(this, (short) 2).execute(newText);

        }

        return true;
    }



}
