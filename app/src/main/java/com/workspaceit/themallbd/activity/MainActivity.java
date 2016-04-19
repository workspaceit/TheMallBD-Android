package com.workspaceit.themallbd.activity;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.GridViewProductsInHomePageAdapter;
import com.workspaceit.themallbd.adapter.HorizontalRVAFeaturedProductsAdapter;
import com.workspaceit.themallbd.adapter.HorizontalRecyclerViewAdapter;
import com.workspaceit.themallbd.adapter.SearchProductAdapter;
import com.workspaceit.themallbd.asynctask.CategoryInListViewAsyncTask;
import com.workspaceit.themallbd.asynctask.GetAllProductForGridViewAsyncTask;
import com.workspaceit.themallbd.asynctask.GetFeaturedProductsAsyncTask;
import com.workspaceit.themallbd.asynctask.GetNewProductsAsyncTask;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.InternetConnection;
import com.workspaceit.themallbd.utility.AllProductGridView;
import com.workspaceit.themallbd.utility.AutoCompleteTextChangeLisnter;
import com.workspaceit.themallbd.utility.DividerItemDecoration;

import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.RecyclerItemClickListener;
import com.workspaceit.themallbd.utility.Utility;

import java.io.Serializable;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AbsListView.OnScrollListener, Serializable {

    private SliderLayout sliderShow;

    //  Adapters
    public HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;
    public HorizontalRVAFeaturedProductsAdapter horizontalRVAFeaturedProductsAdapter;
    public GridViewProductsInHomePageAdapter gridViewProductsInHomePageAdapter;

    //TextViews
    private TextView userNameTextView, emailTextView;

    //Imageview
    private ImageView categoryWomenView, categoryBabyView, categoryMenView, categoryAllView;

    //recycler view variables for horizontal scrolling
    public RecyclerView newProductHorizontalListRV, featuredProductHorizontalListRV;
    private AllProductGridView gridViewForAllProducts;

    public static ArrayList<Products> newProductsForHorizontalViewList;
    public static ArrayList<Products> featuredProductsForHorizontalViewList;
    public static ArrayList<Products> allProductsForGridViewList;

    private InternetConnection mInternetConnection;


    int offsetForNewProductsHorizontalScrolling = 0;
    int offsetForFeaturedProductsHorizontalScrolling = 0;
    int offsetForAllProductsInGridView = 0;

    int limit = 5;
    int limitForProductsInGridView = 4;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int pastVisibleItemsInGridView, visibleItemCountInGridView, totalItemCountInGridView;

    private boolean userScrolled;
    private boolean userScrolledInGridView;

    private boolean noMoreItem;
    private boolean noMoreItemInGridView;
    int lastlastitem = 0;
    private Button cartButton;
    private ImageButton searchButton;
    private AutoCompleteTextView homeSearcTextView;
    private ScrollView mainScroll;
    SearchProductAdapter searchProductAdapter;


    TextView firstCategoryText, secondCategoryText, thirdCategoryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mInternetConnection = new InternetConnection(this);


        this.initialize();
        this.initializeSlider();
        this.initilizeParentCategoryList();


    }

    public void initialize() {
        newProductsForHorizontalViewList = new ArrayList<>();
        featuredProductsForHorizontalViewList = new ArrayList<>();
        allProductsForGridViewList = new ArrayList<>();

        this.userScrolled = true;
        this.noMoreItem = false;

        sliderShow = (SliderLayout) findViewById(R.id.slider);
        //initializing new product horizontal scrolling section
        initializeNewProductHorizontalSection();

        //initializing feature product horizontal scrolling section
        initializeFeaturedProductHorizontalSection();

        //initializing gridview for all products
        initializeGridViewForAllProductsSection();


     /*   String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);*/
        this.homeSearcTextView = (AutoCompleteTextView) findViewById(R.id.search_in_home);
        searchProductAdapter=new SearchProductAdapter(this);
        homeSearcTextView.addTextChangedListener(new AutoCompleteTextChangeLisnter(this));


        homeSearcTextView.setAdapter(searchProductAdapter);
        homeSearcTextView.setThreshold(1);



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




    private void initilizeParentCategoryList() {

        new CategoryInListViewAsyncTask(this).execute();


    }

    private void initializeNewProductHorizontalSection() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newProductHorizontalListRV = (RecyclerView) findViewById(R.id.rv_horizontal);
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
                Log.v("taiful", String.valueOf(dx) + " " + String.valueOf(dy));
                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (userScrolled) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrolled = false;
                            Log.v("...", "Last Item Wow !");
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

        if (MainActivity.featuredProductsForHorizontalViewList.size() < 1) {
            if (mInternetConnection.isConnectingToInternet()) {
                MainActivity.featuredProductsForHorizontalViewList.clear();
                new GetFeaturedProductsAsyncTask(this).execute(
                        String.valueOf(offsetForFeaturedProductsHorizontalScrolling),
                        String.valueOf(limit));
            }
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

                    if (userScrolled) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrolled = false;
                            Log.v("...", "Last Item Wow !");
                            loadFeatureProductMore();
                        }
                    }
                }
            }
        });
    }

    private void loadFeatureProductMore() {
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

    private void initializeGridViewForAllProductsSection() {


        gridViewForAllProducts = (AllProductGridView) findViewById(R.id.gridView_all_Product);


        gridViewForAllProducts.setOnItemClickListener(this);
        gridViewForAllProducts.setOnScrollListener(this);


        this.gridViewProductsInHomePageAdapter = new GridViewProductsInHomePageAdapter(this);
        gridViewForAllProducts.setAdapter(gridViewProductsInHomePageAdapter);


        if (mInternetConnection.isConnectingToInternet()) {
            MainActivity.allProductsForGridViewList.clear();
            new GetAllProductForGridViewAsyncTask(this).execute(String.valueOf(offsetForAllProductsInGridView),
                    String.valueOf(limitForProductsInGridView));
        }


    }

    public void initializeSlider() {
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("banner image")
                .image(R.drawable.banner_image2);
        sliderShow.addSlider(textSliderView);

        TextSliderView textSliderView1 = new TextSliderView(this);
        textSliderView1
                .description("banner image")
                .image(R.drawable.banner_image3);
        sliderShow.addSlider(textSliderView1);

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("banner image")
                .image(R.drawable.banner_image1);

        sliderShow.addSlider(textSliderView2);
    }

    private void loadNewProductMore() {

        if (mInternetConnection.isConnectingToInternet()) {
            offsetForNewProductsHorizontalScrolling += 1;

            new GetNewProductsAsyncTask(this).execute(String.valueOf(offsetForNewProductsHorizontalScrolling), String.valueOf(limit));

        }
    }

    public void setNewProductsList(ArrayList<Products> productsList) {
        System.out.println("productArrayListSize:" + productsList.size());

        for (int i = 0; i < productsList.size(); i++) {
            try {
                System.out.println("product id:" + productsList.get(i).id);
                MainActivity.newProductsForHorizontalViewList.add(productsList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + MainActivity.newProductsForHorizontalViewList.size());
        //  this.horizontalListViewOfProductsAdapter.notifyDataSetChanged();
        this.horizontalRecyclerViewAdapter.notifyDataSetChanged();

    }

    public void setSeacrhAdater(){
        searchProductAdapter.notifyDataSetChanged();
    }
    public void setNewProductListError() {

        userScrolled = false;
        noMoreItem = true;
        Toast.makeText(this, "No Data for new products", Toast.LENGTH_SHORT).show();
    }

    public void setFeaturedProductsList(ArrayList<Products> productList) {

        System.out.println("productArrayListSize:" + productList.size());

        for (int i = 0; i < productList.size(); i++) {
            try {
                System.out.println("product id:" + productList.get(i).id);
                MainActivity.featuredProductsForHorizontalViewList.add(productList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + MainActivity.featuredProductsForHorizontalViewList.size());
        //  this.horizontalListViewOfProductsAdapter.notifyDataSetChanged();
        this.horizontalRVAFeaturedProductsAdapter.notifyDataSetChanged();
    }

    public void setFeaturedProductListError() {
        userScrolled = false;
        noMoreItem = true;
        Toast.makeText(this, "No Data for featured products", Toast.LENGTH_SHORT).show();
    }

    public void setAllProductList(ArrayList<Products> productsList) {
        System.out.print("allProductsListSize:" + productsList.size());

        for (int i = 0; i < productsList.size(); i++) {
            try {
                System.out.println("product id:" + productsList.get(i).id);
                MainActivity.allProductsForGridViewList.add(productsList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userScrolledInGridView = true;
        noMoreItemInGridView = false;
        System.out.println("Final Data Limit:" + MainActivity.allProductsForGridViewList.size());
        this.gridViewProductsInHomePageAdapter.notifyDataSetChanged();

    }

    public void setAllProductsListError() {
        userScrolledInGridView = false;
        noMoreItemInGridView = true;

    }


    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        cartButton.setText(String.valueOf(Utility.shoppingCart.shoppingCartCell.size() + ""));
        cartButton.setOnClickListener(this);

        return true;
    }

    @Override
    public void onClick(View v) {

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
            if(mainScroll.getScrollY()==0){
                homeSearcTextView.requestFocus();
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
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
                            getSystemService(Context.INPUT_METHOD_SERVICE);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            userScrolledInGridView = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        int lastInScreen = firstVisibleItem + visibleItemCount;
        System.out.println("lastinscreen: " + lastInScreen);
        System.out.println("totalItemCount: " + totalItemCount);
        if (lastInScreen >= totalItemCount && userScrolledInGridView && !noMoreItemInGridView) {
            Log.d("TAG", "onScroll lastInScreen - so load more");
            System.out.println("getCalled");
            if (lastlastitem != lastInScreen) {
                lastlastitem = lastInScreen;
                //  if (Utility.current_number < Utility.total) {
                //TODO onscroll load more data
                if (mInternetConnection.isConnectingToInternet()) {
                    // search.offset = Utility.page_number;
                    offsetForAllProductsInGridView += 1;
                    System.out.println("I am going for loading more contents with offset:" + offsetForAllProductsInGridView);
                    new GetAllProductForGridViewAsyncTask(this).execute(String.valueOf(offsetForAllProductsInGridView),
                            String.valueOf(limitForProductsInGridView));


                }
            }


        }
    }

}
