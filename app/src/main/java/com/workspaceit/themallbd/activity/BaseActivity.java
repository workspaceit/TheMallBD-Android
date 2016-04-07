package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.CustomDialog;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.SaveImageToLocal;
import com.workspaceit.themallbd.utility.SessionManager;
import com.workspaceit.themallbd.utility.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button CARTCOUNT;
    // public static int mCARTCOUNT = 0;
    private TextView cartTV;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    private String accessToken;
    private CircleImageView profileImage;

    private TextView userNameTextView, emailTextView, logoutTextView;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager = new SessionManager(this);
        accessToken = sessionManager.getEmail();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (toolbar != null) {
            invalidateOptionsMenu();
        }

        if (userNameTextView != null) {
            if (sessionManager.checkLogin()) {
                userNameTextView.setText(sessionManager.getFullName());
                emailTextView.setText(sessionManager.getEmail());
                logoutTextView.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.logo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.action_bar_gradient));
        }

        initializeNavigationView();

        //customization in header view of navigation drawer
        // customizationOfHeaderView();

    }

    public void initializeNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
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

    public void customizationOfHeaderView() {

        View header = navigationView.getHeaderView(0);

        userNameTextView = (TextView) header.findViewById(R.id.username_in_navigation);
        emailTextView = (TextView) header.findViewById(R.id.email_in_navigation);
        logoutTextView = (TextView) header.findViewById(R.id.logout_in_nav);
        profileImage = (CircleImageView) header.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

            }
        });

       if(sessionManager.getProfileImageUri().toString().equals("")){

       }else {
           String imagePath=sessionManager.getProfileImageUri();

            Uri imageUri = Uri.parse(imagePath);
           Uri dimageUri=Uri.fromFile(new File(imageUri.toString()));
                   Log.v("taiful", imageUri.toString());
           Picasso.with(this)
                   .load(dimageUri)
                   .fit()
                   .into(profileImage);
       }



        if (sessionManager.checkLogin()) {
            userNameTextView.setText(sessionManager.getFullName());
            emailTextView.setText(sessionManager.getEmail());
            logoutTextView.setVisibility(View.VISIBLE);


            logoutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userNameTextView.setText("Login");
                    emailTextView.setText("Register");
                    logoutTextView.setVisibility(View.GONE);
                    sessionManager.logoutUser();
                }
            });


            userNameTextView.setOnClickListener(null);
            emailTextView.setOnClickListener(null);
        } else {
            userNameTextView.setText("Login");
            emailTextView.setText("Register");
            logoutTextView.setVisibility(View.GONE);
            userNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
            });
            emailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    new SaveImageToLocal().createDirectoryAndSaveFile(sessionManager,bitmap,Utility.loggedInUser.user.firstName);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Picasso.with(this)
                        .load(selectedImageUri)
                        .fit()
                        .into(profileImage);


            }
        }
    }




    /**
     * helper to retrieve the path of an image URI
     */


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
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;
            case R.id.nav_cart_id:
                Intent cartIntent = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(cartIntent);
                return true;
            case R.id.nav_about_id:
                Intent contactUs = new Intent(this, ContactUsActivity.class);
                startActivity(contactUs);
                return true;
            case R.id.nav_app_id:
                if (sessionManager.checkLogin()) {

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }
                return true;
            case R.id.nav_favorite_id:
                if (sessionManager.checkLogin()) {

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }
                return true;

            case R.id.nav_help_id:
                Toast.makeText(getApplicationContext(), "help Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_my_mallbd_id:
                if (sessionManager.checkLogin()) {
                    Intent myAccount = new Intent(getApplicationContext(), MyAccountActivity.class);
                    startActivity(myAccount);

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }

                return true;
            case R.id.nav_settings_id:
                if (sessionManager.checkLogin()) {

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }
                return true;

            case R.id.nav_wishlist_id:
                if (sessionManager.checkLogin()) {

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.cart_update_count);
        View view = MenuItemCompat.getActionView(item);
        // CARTCOUNT = (Button) MenuItemCompat.getActionView(item);
        //  CARTCOUNT.setText(mCARTCOUNT+"");
        // CARTCOUNT.setOnClickListener(this);

        CARTCOUNT = (Button) view.findViewById(R.id.notif_count);
        CARTCOUNT.setText(String.valueOf(Utility.shoppingCart.shoppingCartCell.size() + ""));
        CARTCOUNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
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
        if (id == R.id.action_logout) {
            // sessionManager.logoutUser();
            return true;
        }
        if (id == R.id.action_cart) {
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }


}
