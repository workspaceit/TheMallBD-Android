package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.picasso.Picasso;
import com.themallbd.workspaceit.asynctask.LoginByAccesTokenAsynTask;
import com.themallbd.workspaceit.dataModel.MallBdPackageCell;
import com.themallbd.workspaceit.dataModel.ProductCell;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.utility.CustomDialog;
import com.themallbd.workspaceit.utility.SaveImageToLocal;
import com.themallbd.workspaceit.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    // private Button CARTCOUNT;

    // public static int mCARTCOUNT = 0;
    private TextView cartTV;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    private String accessToken;


    private InternetConnection internetConnection;
    private static boolean firstTimeAppAccess=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {


            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            sessionManager = new SessionManager(getApplicationContext());
            sessionManager = new SessionManager(this);
            accessToken = sessionManager.getAccessToken();
            internetConnection = new InternetConnection(this);


            if (firstTimeAppAccess) {

                if (sessionManager.checkLogin() && internetConnection.checkInternet()) {
                    System.out.println("calling " + accessToken);
                    new LoginByAccesTokenAsynTask().execute(accessToken);
                }
                LocalShoppintCart localShoppintCart = new LocalShoppintCart(this);
                String oldProductCart = localShoppintCart.getProductCart();
                String oldPackageCart = localShoppintCart.getPackageCart();
                Gson gson = new Gson();
                ProductCell[] productCells = gson.fromJson(oldProductCart, ProductCell[].class);
                MallBdPackageCell[] mallBdPackageCells = gson.fromJson(oldPackageCart, MallBdPackageCell[].class);
                Utility.shoppingCart.productCell.clear();
                if (productCells != null) {
                    Collections.addAll(Utility.shoppingCart.productCell, productCells);
                }

                Utility.shoppingCart.mallBdPackageCell.clear();
                if (mallBdPackageCells != null) {
                    Collections.addAll(Utility.shoppingCart.mallBdPackageCell, mallBdPackageCells);
                }


                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .resetViewBeforeLoading(true)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .resetViewBeforeLoading(false)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                        .build();


                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .defaultDisplayImageOptions(defaultOptions)
                        .threadPoolSize(5)
                        .memoryCache(new WeakMemoryCache())
                        .threadPriority(Thread.MIN_PRIORITY + 3)
                        .memoryCacheSize(2 * 1024 * 1024)
                        .build();

                ImageLoader.getInstance().init(config);
                firstTimeAppAccess=false;

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (toolbar != null) {
            invalidateOptionsMenu();
        }

        if (sessionManager.isLoggedIn()){
            navigationView.getMenu().findItem(R.id.nav_login_id).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(true);
        }else {
            navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login_id).setVisible(true);
        }
    }


    public void hideNShowLoginButton(){
        navigationView.getMenu().findItem(R.id.nav_login_id).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(false);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //getSupportActionBar().setIcon(R.drawable.logo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.action_bar_gradient));

        }

         initializeNavigationView();
        if (Utility.isLoggedInFlag){
            navigationView.getMenu().findItem(R.id.nav_login_id).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(true);
        }else {
            navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login_id).setVisible(true);
        }

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
               // customizationOfHeaderView();
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


        actionBarDrawerToggle.syncState();
    }












    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);


        //Closing drawer on item click
        drawerLayout.closeDrawers();

        //Intent intent;
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_home_id:
                //intent = new Intent(getApplicationContext(), MainActivity.class);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_cart_id:
                Intent cartIntent = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(cartIntent);
                return true;
            case R.id.nav_about_id:
                if (Utility.isLoggedInFlag) {

                    return true;
                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");

                }

                return true;

            case R.id.nav_app_id:
                if (Utility.isLoggedInFlag) {
                    Intent contactUs=new Intent(this,ContactUsActivity.class);
                    startActivity(contactUs);
                    return true;
                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");

                }

                return true;

            case R.id.nav_login_id:
                Intent loginIntent=new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                return true;

            case R.id.nav_my_mallbd_id:
                if (Utility.isLoggedInFlag) {
                    Intent myAccount = new Intent(getApplicationContext(), MyAccountActivity.class);
                    startActivity(myAccount);
                    return true;

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }

                return true;


            case R.id.nav_wishlist_id:
                if (Utility.isLoggedInFlag) {
                    Utility.wishlistProductArrayList.clear();
                    Intent wishIntent = new Intent(getApplicationContext(), WishListActivity.class);
                    startActivity(wishIntent);

                } else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see this feature");
                }

                return true;
            case R.id.nav_logout_id:
                if (Utility.isLoggedInFlag) {


                    CustomDialog.logoutDailog(this, sessionManager, "Logout", "Confirm Logout?");

                } else {
                    CustomDialog.showDailog(this, "You need to login first", "You are not logged in");

                }
                return true;

            case R.id.nav_order_history:
                if(Utility.isLoggedInFlag){
                    Intent orderInten=new Intent(getApplicationContext(),PrevoiusOrderActivity.class);
                    startActivity(orderInten);

                }else {
                    CustomDialog.showDailog(this, "You Need to Login First", "You need to do login to see your previous order history");
                }
                return true;

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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
