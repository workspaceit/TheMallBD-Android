package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
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
import com.themallbd.workspaceit.utility.LocalShoppintCart;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.SessionManager;
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
    private CircleImageView profileImage;

    private TextView userNameTextView;
    private static final int SELECT_PICTURE = 1;
    private static boolean IMAGE_LOADER_FLAG=true;
    private InternetConnection internetConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager = new SessionManager(this);
        accessToken = sessionManager.getAccessToken();
        internetConnection=new InternetConnection(this);



        if(true) {

            if(sessionManager.checkLogin() && internetConnection.checkInternet() ) {
                System.out.println("calling " + accessToken);
                new LoginByAccesTokenAsynTask().execute(accessToken);
            }
            LocalShoppintCart localShoppintCart=new LocalShoppintCart(this);
            String oldProductCart=localShoppintCart.getProductCart();
            String oldPackageCart=localShoppintCart.getPackageCart();
            Gson gson=new Gson();
            ProductCell[] productCells=gson.fromJson(oldProductCart,ProductCell[].class);
            MallBdPackageCell[]mallBdPackageCells=gson.fromJson(oldPackageCart,MallBdPackageCell[].class);
            Utility.shoppingCart.productCell.clear();
            if(productCells!=null) {
                Collections.addAll(Utility.shoppingCart.productCell, productCells);
            }

            Utility.shoppingCart.mallBdPackageCell.clear();
            if(mallBdPackageCells!=null){
                Collections.addAll(Utility.shoppingCart.mallBdPackageCell,mallBdPackageCells);
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
            IMAGE_LOADER_FLAG=false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (toolbar != null) {
            invalidateOptionsMenu();
        }

        if (userNameTextView != null) {
            if (sessionManager.checkLogin()) {
                userNameTextView.setText(sessionManager.getFullName());


                navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(true);

                if (!sessionManager.getProfileImageUri().toString().equals("")) {
                    String imagePath = sessionManager.getProfileImageUri();

                    Uri imageUri = Uri.parse(imagePath);
                    Uri dimageUri = Uri.fromFile(new File(imageUri.toString()));

                    Picasso.with(this)
                            .load(dimageUri)
                            .fit()
                            .into(profileImage);
                }
            } else {
                navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(false);
                userNameTextView.setText("Login");
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
       // initializeRightNavigationView();

        //customization in header view of navigation drawer
            //customizationOfHeaderView();

    }

    public void initializeRightNavigationView(){
        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.hamburger_2);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        actionBarDrawerToggle.syncState();
    }

    public void customizationOfHeaderView() {

        View header = navigationView.getHeaderView(0);
        userNameTextView = (TextView) header.findViewById(R.id.username_in_navigation);
        profileImage = (CircleImageView) header.findViewById(R.id.profile_image);

        if (Utility.isLoggedInFlag) {
            userNameTextView.setText(sessionManager.getFullName());
            navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(true);

        } else {
            navigationView.getMenu().findItem(R.id.nav_logout_id).setVisible(false);
            userNameTextView.setText("Login");
        }


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.checkLogin()) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
                }

            }
        });


        userNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sessionManager.checkLogin()) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });


        if (sessionManager.getProfileImageUri().toString().equals("") || !sessionManager.checkLogin()) {
            Picasso.with(this)
                    .load(R.drawable.icon_un)
                    .into(profileImage);

        } else {
            String imagePath = sessionManager.getProfileImageUri();

            Uri imageUri = Uri.parse(imagePath);
            Uri dimageUri = Uri.fromFile(new File(imageUri.toString()));

            Picasso.with(this)
                    .load(dimageUri)
                    .fit()
                    .into(profileImage);
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    new SaveImageToLocal().createDirectoryAndSaveFile(sessionManager, bitmap, Utility.loggedInUser.user.firstName);
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


                    CustomDialog.logoutDailog(this, sessionManager, "Logout", "Confrim Logout?");

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

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItem item1 = menu.findItem(R.id.action_search);

        MenuItemCompat.setActionView(item1, R.layout.toolbar_search_icon);
        MenuItemCompat.setActionView(item, R.layout.cart_update_count);
        View view = MenuItemCompat.getActionView(item);
        View searchView = MenuItemCompat.getActionView(item1);


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
    }*/


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
