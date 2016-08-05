package com.themallbd.workspaceit.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.themallbd.workspaceit.adapter.SearchProductAdapter;
import com.themallbd.workspaceit.asynctask.ProductSearchAsynTask;
import com.themallbd.workspaceit.utility.CustomAutoCompleteTextView;
import com.themallbd.workspaceit.utility.LocalShoppintCart;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.SessionManager;
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
        super.onResume();
        if (toolbar != null)
            invalidateOptionsMenu();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle=(TextView)toolbar.findViewById(R.id.toolbar_title);

        toolBarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainIntent);
            }
        });

        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setIcon(R.drawable.logo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.action_bar_gradient));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.cart_update_count);


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
        CARTCOUNT.setText(String.valueOf((Utility.shoppingCart.productCell.size() + Utility.shoppingCart.mallBdPackageCell.size())+""));

        CARTCOUNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(intent);

            }
        });


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
