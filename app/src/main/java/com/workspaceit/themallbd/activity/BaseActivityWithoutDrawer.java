package com.workspaceit.themallbd.activity;

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
import android.widget.Button;
import android.widget.TextView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.SessionManager;
import com.workspaceit.themallbd.utility.Utility;

public class BaseActivityWithoutDrawer extends AppCompatActivity {

    private Button CARTCOUNT;
    //public static int mCARTCOUNT = 0;
    private TextView cartTV;

    private Toolbar toolbar;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(toolbar!=null)
            invalidateOptionsMenu();
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
        View view = MenuItemCompat.getActionView(item);
        // CARTCOUNT = (Button) MenuItemCompat.getActionView(item);
        //  CARTCOUNT.setText(mCARTCOUNT+"");
        // CARTCOUNT.setOnClickListener(this);

        CARTCOUNT = (Button)view.findViewById(R.id.notif_count);
        CARTCOUNT.setText(String.valueOf(Utility.shoppingCart.shoppingCartCell.size()+""));
        CARTCOUNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckoutActivity.class);
                startActivity(intent);
            }
        });

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
        if (id == R.id.action_logout)
        {
            sessionManager.logoutUser();
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
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
