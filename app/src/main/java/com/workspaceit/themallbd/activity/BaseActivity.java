package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.SessionManager;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;

    private TextView userNameTextView,emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setIcon(R.drawable.logo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.action_bar_gradient));
        }

        initializeNavigationView();

        //customization in header view of navigation drawer
       // customizationOfHeaderView();

    }

    public void initializeNavigationView(){
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as
                // we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as
                // we dont want anything to happen so we leave this blank
                customizationOfHeaderView();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

            }

            @Override
            public void onDrawerStateChanged(int arg0) {

            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void customizationOfHeaderView(){

        View header = navigationView.getHeaderView(0);

        userNameTextView = (TextView) header.findViewById(R.id.username_in_navigation);
        emailTextView = (TextView) header.findViewById(R.id.email_in_navigation);

        if (sessionManager.checkLogin()) {
            userNameTextView.setText(sessionManager.getFullName());
            emailTextView.setText(sessionManager.getEmail());

            userNameTextView.setOnClickListener(null);
            emailTextView.setOnClickListener(null);
        }
        else {
            userNameTextView.setText("Login");
            emailTextView.setText("Register");
            userNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
            });
            emailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        Intent intent;
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_home_id:
                intent = new Intent(getApplicationContext(),MainActivity.class);
                break;
            case R.id.nav_cart_id:
                intent = new Intent(getApplicationContext(),ProductDetailsActivity.class);
                break;
            case R.id.nav_about_id:
                Toast.makeText(getApplicationContext(), "about Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_app_id:
                Toast.makeText(getApplicationContext(), "app feedback Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_favorite_id:
                Toast.makeText(getApplicationContext(), "favorite Mail Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_help_id:
                Toast.makeText(getApplicationContext(), "help Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_my_mallbd_id:
                Toast.makeText(getApplicationContext(), "my mall bd Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_settings_id:
                Toast.makeText(getApplicationContext(), "settings Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_wishlist_id:
                Toast.makeText(getApplicationContext(), "wishlist Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
        startActivity(intent);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
