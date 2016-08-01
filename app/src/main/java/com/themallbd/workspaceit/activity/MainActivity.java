package com.themallbd.workspaceit.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.themallbd.workspaceit.adapter.DiscountProductRecyleViewAdapter;
import com.themallbd.workspaceit.adapter.PackageInHorizontalListAdapter;
import com.themallbd.workspaceit.asynctask.GetAllDeliveryMethodsAsyncTask;
import com.themallbd.workspaceit.asynctask.GetAllPaymentMethodAsynTask;
import com.themallbd.workspaceit.asynctask.GetBannerImagesAsyncTask;
import com.themallbd.workspaceit.asynctask.GetPackagesAsynTask;
import com.themallbd.workspaceit.asynctask.GetSpecailDiscountProductAsynTask;
import com.themallbd.workspaceit.dataModel.Banner;

import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.utility.CustomSliderView;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.adapter.GridViewProductsInHomePageAdapter;
import com.themallbd.workspaceit.adapter.HorizontalRVAFeaturedProductsAdapter;
import com.themallbd.workspaceit.adapter.HorizontalRecyclerViewAdapter;
import com.themallbd.workspaceit.adapter.SearchProductAdapter;
import com.themallbd.workspaceit.asynctask.CategoryInListViewAsyncTask;
import com.themallbd.workspaceit.asynctask.GetAllProductForGridViewAsyncTask;
import com.themallbd.workspaceit.asynctask.GetFeaturedProductsAsyncTask;
import com.themallbd.workspaceit.asynctask.GetNewProductsAsyncTask;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.AllProductGridView;
import com.themallbd.workspaceit.utility.AutoCompleteTextChangeLisnter;
import com.themallbd.workspaceit.utility.CustomAutoCompleteTextView;
import com.themallbd.workspaceit.utility.DividerItemDecoration;

import com.themallbd.workspaceit.utility.RecyclerItemClickListener;
import com.themallbd.workspaceit.utility.Utility;

import java.io.Serializable;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, Serializable {

    private SliderLayout sliderShow;

    //  Adapters
    private HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;
    private HorizontalRVAFeaturedProductsAdapter horizontalRVAFeaturedProductsAdapter;
    private GridViewProductsInHomePageAdapter gridViewProductsInHomePageAdapter;
    private DiscountProductRecyleViewAdapter discountProductRecyleViewAdapter;
    private PackageInHorizontalListAdapter packageInHorizontalListAdapter;

    private Button showAllNewProductButton,showAllFeatureProductButton,showAllPackageButton,showAllDiscountButton;
    private ProgressBar gridViewProgressBar;


    //Imageview
    private ImageView categoryWomenView, categoryBabyView, categoryMenView, categoryAllView;

    //recycler view variables for horizontal scrolling
    private RecyclerView newProductHorizontalListRV, featuredProductHorizontalListRV, packgeProductHorizontalListRV, specailDiscountProductHorizonatlRV;
    private AllProductGridView gridViewForAllProducts;

    public static ArrayList<Products> newProductsForHorizontalViewList;
    public static ArrayList<Products> featuredProductsForHorizontalViewList;
    public static ArrayList<Products> allProductsForGridViewList;
    public static ArrayList<MallBdPackage> packgeProductForHorizontalList;
    public static ArrayList<Products> discountProductForHorizontalList;



    private InternetConnection mInternetConnection;


    private int offsetForNewProductsHorizontalScrolling = 0;
    private int offsetForFeaturedProductsHorizontalScrolling = 0;
    private int offsetForAllProductsInGridView = 0;
    private int offsetForDiscountProduct=0;
    private int offsetForPackage=0;

    int limit = 5;
    int limitForProductsInGridView = 4;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
   

    private boolean userScrolledForNewProduct;
    private boolean userScrollForFeatureProduct;
    private boolean userScrollForPackge;
    private boolean userScrollForDiscountProduct;
    private boolean userScrolledInGridView;

    public static boolean moreItemNewProduct;
    public static boolean moreItemFeatureProduct;
    public static boolean morePackage;
    public static boolean moreDiscountProduct;
    private boolean noMoreItemInGridView;
    int lastlastitem = 0;
    private Button cartButton;
    private ImageButton searchButton;
    public AutoCompleteTextView homeSearcTextView;
    private ScrollView mainScroll;
    public SearchProductAdapter searchProductAdapter;
    public String[] countries;
    private boolean gridAllFlag=true;
    TextView firstCategoryText, secondCategoryText, thirdCategoryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mInternetConnection = new InternetConnection(this);

        if(!mInternetConnection.isConnectingToInternet()){
            Intent intent=new Intent(this,NoInternetActiviy.class);
            startActivity(intent);
            return;
        }

        this.initialize();
        this.getNecessaryData();
        this.initilizeParentCategoryList();


    }


    private void getNecessaryData(){

        if(Utility.banners.size()<1) {
            new GetBannerImagesAsyncTask(this).execute();
        }else {
            this.initializeSlider();
        }

        if(Utility.deliveryMethods.size()<1){
            new GetAllDeliveryMethodsAsyncTask().execute();
        }

        if (Utility.paymentMethodses.size()<1){
            new GetAllPaymentMethodAsynTask().execute();
        }

    }

    private void initialize() {


        this.gridViewProgressBar=(ProgressBar)findViewById(R.id.grid_view_progress_bar);
        newProductsForHorizontalViewList = new ArrayList<>();
        featuredProductsForHorizontalViewList = new ArrayList<>();
        allProductsForGridViewList = new ArrayList<>();
        packgeProductForHorizontalList =new ArrayList<>();
        discountProductForHorizontalList=new ArrayList<>();

        this.userScrolledForNewProduct = true;
        this.userScrollForFeatureProduct = true;
        this.userScrollForPackge=true;
        this.userScrollForDiscountProduct=true;

        moreItemFeatureProduct = true;
        moreItemNewProduct = true;
        morePackage=true;
        moreDiscountProduct=true;

        sliderShow = (SliderLayout) findViewById(R.id.slider);
        //initializing new product horizontal scrolling section
        initializeNewProductHorizontalSection();

        //initializing feature product horizontal scrolling section
        initializeFeaturedProductHorizontalSection();

        //initializing gridview for all products
        initializeGridViewForAllProductsSection();

        initializeDiscountProductForHorizontalSection();

        initializePackageProductForHorizontalList();


        //countries=getResources().getStringArray(R.array.countries_array);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        this.homeSearcTextView = (CustomAutoCompleteTextView) findViewById(R.id.search_in_home);

        homeSearcTextView.setThreshold(3);
        homeSearcTextView.addTextChangedListener(new AutoCompleteTextChangeLisnter(this));
        homeSearcTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (homeSearcTextView.getText().toString().equals("")) {
                    MakeToast.showToast(getApplicationContext(), "You didn't type anything...");
                    return false;
                }
                Intent intent = new Intent(getApplicationContext(), SearchProductListActivity.class);
                intent.putExtra("keyword", homeSearcTextView.getText().toString());
                startActivity(intent);
                return true;
            }
        });


        this.showAllNewProductButton=(Button)findViewById(R.id.more_new_product_button);
        this.showAllFeatureProductButton=(Button)findViewById(R.id.more_feature_product_button);
        this.showAllPackageButton=(Button)findViewById(R.id.more_package_list_button);
        this.showAllDiscountButton=(Button)findViewById(R.id.discount_prouct_more_button);
        showAllNewProductButton.setOnClickListener(this);
        showAllFeatureProductButton.setOnClickListener(this);
        showAllPackageButton.setOnClickListener(this);
        showAllDiscountButton.setOnClickListener(this);

        this.mainScroll = (ScrollView) findViewById(R.id.scroll_main_view);
        this.categoryWomenView = (ImageView) findViewById(R.id.iv_home_women);
        this.categoryWomenView.setOnClickListener(this);

        this.categoryBabyView = (ImageView) findViewById(R.id.imageView_babyCare);
        this.categoryBabyView.setOnClickListener(this);

        this.categoryMenView = (ImageView) findViewById(R.id.iv_home_men);
        this.categoryMenView.setOnClickListener(this);

        this.categoryAllView = (ImageView) findViewById(R.id.iv_home_all);
        this.categoryAllView.setOnClickListener(this);
        firstCategoryText = (TextView) findViewById(R.id.tv_home_cat_first);
        secondCategoryText = (TextView) findViewById(R.id.tv_home_second);
        thirdCategoryText = (TextView) findViewById(R.id.tv_home_third);

    }


    private void initializePackageProductForHorizontalList(){

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        packgeProductHorizontalListRV= (RecyclerView) findViewById(R.id.packge_list_reclyeview);
        packgeProductHorizontalListRV.setLayoutManager(layoutManager);
        // Create adapter passing in the sample user data
        this.packageInHorizontalListAdapter = new PackageInHorizontalListAdapter(this);
        // Attach the adapter to the recyclerview to populate items
        this.packgeProductHorizontalListRV.setAdapter(packageInHorizontalListAdapter);
        this.packgeProductHorizontalListRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        // Set layout manager to position the items




        if (MainActivity.packgeProductForHorizontalList.size() < 1) {
            if (mInternetConnection.isConnectingToInternet())

            {
                MainActivity.packgeProductForHorizontalList.clear();
                new GetPackagesAsynTask(this).execute(String.valueOf(this.limit), String.valueOf(offsetForPackage));
            }
        }


        this.packgeProductHorizontalListRV.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, PackageDetailsActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("productArray", 1);
                        startActivity(intent);
                    }
                })
        );

        this.packgeProductHorizontalListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                    if (userScrollForPackge && morePackage) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrollForPackge = false;

                            loadMorePackages();
                        }
                    }
                }
            }

        });


    }


    private void initializeDiscountProductForHorizontalSection(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        specailDiscountProductHorizonatlRV= (RecyclerView) findViewById(R.id.specail_product_recyleiew);
        specailDiscountProductHorizonatlRV.setLayoutManager(layoutManager);
        // Create adapter passing in the sample user data
        this.discountProductRecyleViewAdapter = new DiscountProductRecyleViewAdapter(this);
        // Attach the adapter to the recyclerview to populate items
        this.specailDiscountProductHorizonatlRV.setAdapter(discountProductRecyleViewAdapter);
        this.specailDiscountProductHorizonatlRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        // Set layout manager to position the items

        if (MainActivity.discountProductForHorizontalList.size() < 1) {
            if (mInternetConnection.isConnectingToInternet())

            {
                MainActivity.discountProductForHorizontalList.clear();
               new GetSpecailDiscountProductAsynTask(this).execute(String.valueOf(this.limit),String.valueOf(offsetForDiscountProduct));
            }
        }


        this.specailDiscountProductHorizonatlRV.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("productArray", 9);
                        startActivity(intent);
                    }
                })
        );


        this.specailDiscountProductHorizonatlRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                    if (userScrollForDiscountProduct && moreDiscountProduct) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrollForDiscountProduct = false;


                            loadMoreDiscountProducts();
                        }
                    }
                }
            }

        });

    }

    private void initilizeParentCategoryList() {

        if(Utility.parentsCategoryArraylist.size()<1) {

            new CategoryInListViewAsyncTask(this).execute();
        }else {
            this.initializeCategoryView();
        }


    }

    private void initializeNewProductHorizontalSection() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newProductHorizontalListRV = (RecyclerView) findViewById(R.id.rv_horizontal);
       newProductHorizontalListRV.getItemAnimator().setChangeDuration(0);
        newProductHorizontalListRV.setLayoutManager(layoutManager);

        // Create adapter passing in the sample user data
        this.horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(this);
        // Attach the adapter to the recyclerview to populate items
        this.newProductHorizontalListRV.setAdapter(horizontalRecyclerViewAdapter);
        this.newProductHorizontalListRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        // Set layout manager to position the items

        if (MainActivity.newProductsForHorizontalViewList.size() < 1) {
            if (mInternetConnection.isConnectingToInternet())

            {
                MainActivity.newProductsForHorizontalViewList.clear();
                new GetNewProductsAsyncTask(this).execute(
                        String.valueOf(offsetForNewProductsHorizontalScrolling),
                        String.valueOf(limit));
            }
        }


        this.newProductHorizontalListRV.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("productArray", 1);
                        startActivity(intent);
                    }
                })
        );


        this.newProductHorizontalListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                    if (userScrolledForNewProduct && moreItemNewProduct) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrolledForNewProduct = false;


                            loadNewProductMore();
                        }
                    }
                }
            }

        });
    }

    private void initializeFeaturedProductHorizontalSection() {
        final LinearLayoutManager layoutManagerForFeaturedProducts = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        featuredProductHorizontalListRV = (RecyclerView) findViewById(R.id.rv_featured_horizontalProducts);
        featuredProductHorizontalListRV.setLayoutManager(layoutManagerForFeaturedProducts);
        this.horizontalRVAFeaturedProductsAdapter = new HorizontalRVAFeaturedProductsAdapter(this);
        this.featuredProductHorizontalListRV.setAdapter(horizontalRVAFeaturedProductsAdapter);
        this.featuredProductHorizontalListRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));


        if (mInternetConnection.isConnectingToInternet()) {
            MainActivity.featuredProductsForHorizontalViewList.clear();
            new GetFeaturedProductsAsyncTask(this).execute(
                    String.valueOf(offsetForFeaturedProductsHorizontalScrolling),
                    String.valueOf(limit));
        }

        this.featuredProductHorizontalListRV.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("productArray", 2);
                        startActivity(intent);
                    }
                })
        );


        this.featuredProductHorizontalListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManagerForFeaturedProducts.getChildCount();
                    totalItemCount = layoutManagerForFeaturedProducts.getItemCount();
                    pastVisiblesItems = layoutManagerForFeaturedProducts.findFirstVisibleItemPosition();


                    if (userScrollForFeatureProduct && moreItemFeatureProduct) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrollForFeatureProduct = false;
                            loadFeatureProductMore();
                        }
                    }
                }
            }
        });
    }


    private void loadMoreAllProduct() {
            this.gridViewProgressBar.setVisibility(View.VISIBLE);
        if (mInternetConnection.isConnectingToInternet()) {
            this.offsetForAllProductsInGridView++;
            new GetAllProductForGridViewAsyncTask(this).execute(String.valueOf(offsetForAllProductsInGridView),
                    String.valueOf(limitForProductsInGridView));
        }
    }

    private void loadFeatureProductMore() {
        MainActivity.featuredProductsForHorizontalViewList.add(null);
        this.horizontalRVAFeaturedProductsAdapter.notifyItemInserted(MainActivity.featuredProductsForHorizontalViewList.size()-1);

        if (mInternetConnection.isConnectingToInternet()) {
            this.offsetForFeaturedProductsHorizontalScrolling += 1;

            new GetFeaturedProductsAsyncTask(this).execute(String.valueOf(offsetForFeaturedProductsHorizontalScrolling), String.valueOf(limit));

        }

    }


    public void initializeCategoryView() {
        int count = Utility.parentsCategoryArraylist.size();
        count--;
        firstCategoryText.setText(Utility.parentsCategoryArraylist.get(count).title);
        count--;
        secondCategoryText.setText(Utility.parentsCategoryArraylist.get(count).title);
        count--;
        thirdCategoryText.setText(Utility.parentsCategoryArraylist.get(count).title);

    }

    private boolean gridViewScrollFlag = false;

    private void initializeGridViewForAllProductsSection() {


        gridViewForAllProducts = (AllProductGridView) findViewById(R.id.gridView_all_Product);


        gridViewForAllProducts.setOnItemClickListener(this);
        //gridViewForAllProducts.setOnScrollListener(this);


        this.gridViewProductsInHomePageAdapter = new GridViewProductsInHomePageAdapter(this);
        gridViewForAllProducts.setAdapter(gridViewProductsInHomePageAdapter);


        if (mInternetConnection.isConnectingToInternet()) {
            MainActivity.allProductsForGridViewList.clear();
            new GetAllProductForGridViewAsyncTask(this).execute(String.valueOf(offsetForAllProductsInGridView),
                    String.valueOf(limitForProductsInGridView));
        }

      gridViewForAllProducts.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {


              if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                  if(gridAllFlag && !noMoreItemInGridView) {
                        gridAllFlag=false;
                     loadMoreAllProduct();
                  }
              }
              return false;
          }
      });


    }

    public void initializeSlider() {



        if (Utility.banners.size() >= 1) {
            for (Banner banner : Utility.banners) {
                CustomSliderView textSliderView = new CustomSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description("Banner Image")
                        .image(Utility.IMAGE_URL + "/banner/" + banner.image)
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                sliderShow.addSlider(textSliderView);
            }
        } else {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("Banner not found")
                    .image(R.drawable.image_not_found)
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            sliderShow.addSlider(textSliderView);
        }
    }


    private void loadMorePackages(){
        MainActivity.packgeProductForHorizontalList.add(null);
        this.packageInHorizontalListAdapter.notifyItemInserted(packgeProductForHorizontalList.size()-1);

        if (mInternetConnection.isConnectingToInternet()){
            this.offsetForPackage++;
            new GetPackagesAsynTask(this).execute(String.valueOf(this.limit),String.valueOf(this.offsetForPackage));
        }
    }

    private void loadMoreDiscountProducts(){
        MainActivity.discountProductForHorizontalList.add(null);
        this.discountProductRecyleViewAdapter.notifyItemInserted(discountProductForHorizontalList.size()-1);
        if(mInternetConnection.isConnectingToInternet()){
            this.offsetForDiscountProduct++;
            new GetSpecailDiscountProductAsynTask(this).execute(String.valueOf(this.limit), String.valueOf(this.offsetForDiscountProduct));
        }
    }

    private void loadNewProductMore() {

        MainActivity.newProductsForHorizontalViewList.add(null);
        this.horizontalRecyclerViewAdapter.notifyItemInserted(newProductsForHorizontalViewList.size()-1);
        if (mInternetConnection.isConnectingToInternet()) {
            offsetForNewProductsHorizontalScrolling += 1;

            new GetNewProductsAsyncTask(this).execute(String.valueOf(offsetForNewProductsHorizontalScrolling), String.valueOf(limit));

        }
    }


    public void notifyDiscountProductDataSet(){
        this.userScrollForDiscountProduct=true;
        discountProductRecyleViewAdapter.notifyDataSetChanged();
    }

    public void notifyPackageDataSet(){
        this.userScrollForPackge=true;
        packageInHorizontalListAdapter.notifyDataSetChanged();
    }

    public void setNewProductsList() {

        this.userScrolledForNewProduct=true;
        this.horizontalRecyclerViewAdapter.notifyDataSetChanged();



    }

    public void setSeacrhAdater() {
        searchProductAdapter = new SearchProductAdapter(this, R.layout.search_product_row, Utility.searchProductTitle);


        if (Utility.searchProductTitle.size() > 0) {
            homeSearcTextView.setAdapter(searchProductAdapter);
            searchProductAdapter.notifyDataSetChanged();
            homeSearcTextView.showDropDown();
        } else {
            homeSearcTextView.setAdapter(searchProductAdapter);
            searchProductAdapter.notifyDataSetChanged();
        }


    }

    public void setDiscountProductError(){
        this.discountProductRecyleViewAdapter.notifyItemRemoved(MainActivity.discountProductForHorizontalList.size()-1);
        moreDiscountProduct =false;
        this.userScrollForDiscountProduct=false;
        MakeToast.showToast(this,"No More Discount Product...");
    }

    public void setPackageListError(){
        this.packageInHorizontalListAdapter.notifyItemRemoved(MainActivity.packgeProductForHorizontalList.size()-1);
        this.userScrollForPackge=false;
        morePackage=false;
        MakeToast.showToast(this,"No More Package...");
    }

    public void setNewProductListError() {
        this.horizontalRecyclerViewAdapter.notifyItemRemoved(MainActivity.newProductsForHorizontalViewList.size()-1);
        userScrolledForNewProduct = false;
        moreItemNewProduct = false;
        MakeToast.showToast(this,"No More New Products...");

    }

    public void setFeaturedProductsList() {

        this.userScrollForFeatureProduct=true;
        this.horizontalRVAFeaturedProductsAdapter.notifyDataSetChanged();

    }

    public void setFeaturedProductListError() {
        this.horizontalRVAFeaturedProductsAdapter.notifyItemRemoved(MainActivity.featuredProductsForHorizontalViewList.size()-1);
        userScrollForFeatureProduct = false;
        moreItemFeatureProduct = false;
        MakeToast.showToast(this,"No More Feature Products...");

    }

    public void setAllProductList(ArrayList<Products> productsList) {

        this.gridViewProgressBar.setVisibility(View.GONE);
        for (int i = 0; i < productsList.size(); i++) {
            try {

                MainActivity.allProductsForGridViewList.add(productsList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userScrolledInGridView = true;
        noMoreItemInGridView = false;
        gridAllFlag=true;

        this.gridViewProductsInHomePageAdapter.notifyDataSetChanged();

    }

    public void setAllProductsListError() {
        this.gridViewProgressBar.setVisibility(View.GONE);
        userScrolledInGridView = false;
        noMoreItemInGridView = true;
        gridAllFlag=false;

    }





    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
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


        searchButton = (ImageButton) searchView.findViewById(R.id.search_icon_btn);


        searchButton.setOnClickListener(this);


        cartButton = (Button) view.findViewById(R.id.notif_count);
        cartButton.setText(String.valueOf((Utility.shoppingCart.productCell.size() + Utility.shoppingCart.mallBdPackageCell.size())+""));
        cartButton.setOnClickListener(this);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(newProductsForHorizontalViewList.size()>0){
            this.horizontalRecyclerViewAdapter.notifyDataSetChanged();
            this.offsetForNewProductsHorizontalScrolling=((MainActivity.newProductsForHorizontalViewList.size()/5)-1);

        }

        if(featuredProductsForHorizontalViewList.size()>0){
            this.horizontalRecyclerViewAdapter.notifyDataSetChanged();
            this.offsetForFeaturedProductsHorizontalScrolling=((MainActivity.featuredProductsForHorizontalViewList.size()/5)-1);

        }

        if(packgeProductForHorizontalList.size()>0){
            this.packageInHorizontalListAdapter.notifyDataSetChanged();
            this.offsetForPackage=((MainActivity.packgeProductForHorizontalList.size()/5)-1);
        }

        if(discountProductForHorizontalList.size()>0){
            this.discountProductRecyleViewAdapter.notifyDataSetChanged();
            this.offsetForDiscountProduct=((MainActivity.discountProductForHorizontalList.size()/5)-1);
        }


    }

    @Override
    public void onClick(View v) {

        if (v==showAllNewProductButton){
            Intent intent=new Intent(this,AllNewProductActivity.class);
            startActivity(intent);
            return;
        }else if(v==showAllFeatureProductButton){
            Intent intent=new Intent(this,AllFeaturesProductActivity.class);
            startActivity(intent);
            return;
        }else if(v==showAllPackageButton){
            Intent intent=new Intent(this,AllPacakgeActivity.class);
            startActivity(intent);
            return;
        }else if(v==showAllDiscountButton){
            Intent intent=new Intent(this,AllDiscountProductActivity.class);
            startActivity(intent);
            return;
        }

        int count = Utility.parentsCategoryArraylist.size();

        count--;

        if (v == categoryWomenView) {
            Intent intent = new Intent(this, CategoryInExpandableListViewActivity.class);
            intent.putExtra("position", count - 1);
            intent.putExtra("title", Utility.parentsCategoryArraylist.get(count - 1).title);
            startActivity(intent);

        } else if (v == categoryBabyView) {
            Intent intent = new Intent(this, CategoryInExpandableListViewActivity.class);
            intent.putExtra("position", count);
            intent.putExtra("title", Utility.parentsCategoryArraylist.get(count).title);
            startActivity(intent);

        } else if (v == categoryMenView) {
            Intent intent = new Intent(this, CategoryInExpandableListViewActivity.class);
            intent.putExtra("position", count - 2);
            intent.putExtra("title", Utility.parentsCategoryArraylist.get(count - 2).title);
            startActivity(intent);
        } else if (v == categoryAllView) {
            Intent intent = new Intent(MainActivity.this, CategoryListViewActivity.class);
            startActivity(intent);
        } else if (v == cartButton) {
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
            startActivity(intent);
        } else if (v == searchButton) {
            if (mainScroll.getScrollY() == 0) {
                homeSearcTextView.requestFocus();
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(homeSearcTextView, 0);
                return;
            }

            ValueAnimator realSmoothScrollAnimation =
                    ValueAnimator.ofInt(mainScroll.getScrollY(), 0);
            realSmoothScrollAnimation.setDuration(700);
            realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int scrollTo = (Integer) animation.getAnimatedValue();
                    mainScroll.scrollTo(0, scrollTo);
                }


            });

            realSmoothScrollAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    homeSearcTextView.requestFocus();
                    InputMethodManager keyboard = (InputMethodManager)
                            getSystemService(INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(homeSearcTextView, 0);
                }
            });

            realSmoothScrollAnimation.start();


        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("productArray", 3);
                startActivity(intent);
            }
        }, 0);
    }



}
