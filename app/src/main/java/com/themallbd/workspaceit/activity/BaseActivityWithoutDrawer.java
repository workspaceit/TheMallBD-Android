package com.themallbd.workspaceit.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
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
import  android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.themallbd.workspaceit.utility.CustomAutoCompleteTextView;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.SessionManager;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class BaseActivityWithoutDrawer extends AppCompatActivity {


    //public static int mCARTCOUNT = 0;
    private TextView cartTV;

    private Toolbar toolbar;

    private SessionManager sessionManager;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(toolbar!=null)
            invalidateOptionsMenu();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    if(query.equals("")){
                        MakeToast.showToast(getApplicationContext(),"You didn't type anything...");
                        return false;
                    }
                    Intent intent=new Intent(getApplicationContext(),SearchProductListActivity.class);
                    intent.putExtra("keyword",query);
                    startActivity(intent);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }


        View view = MenuItemCompat.getActionView(item);
        Button CARTCOUNT = (Button) view.findViewById(R.id.notif_count);
        CARTCOUNT.setText(String.valueOf(Utility.shoppingCart.shoppingCartCell.size() + ""));


        return true;
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
        }else if (id==R.id.action_cart){
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  /*  public void setNotifCount(int count){
        BaseActivity.mCARTCOUNT = count;
        invalidateOptionsMenu();
    }*/

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        finish();
    }
}
