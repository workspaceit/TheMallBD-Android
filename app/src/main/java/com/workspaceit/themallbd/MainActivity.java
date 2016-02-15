package com.workspaceit.themallbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.workspaceit.themallbd.adapter.GridViewProductsInHomePageAdapter;
import com.workspaceit.themallbd.adapter.HorizontalRVAFeaturedProductsAdapter;
import com.workspaceit.themallbd.adapter.HorizontalRecyclerViewAdapter;
import com.workspaceit.themallbd.asynctask.GetAllProductForGridViewAsyncTask;
import com.workspaceit.themallbd.asynctask.GetFeaturedProductsAsyncTask;
import com.workspaceit.themallbd.asynctask.GetNewProductsAsyncTask;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.InternetConnection;
import com.workspaceit.themallbd.utility.DividerItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private SliderLayout sliderShow;
    private GridView gridViewForAllProducts;

    //  Adapters
    public HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;
    public HorizontalRVAFeaturedProductsAdapter horizontalRVAFeaturedProductsAdapter;
    public GridViewProductsInHomePageAdapter gridViewProductsInHomePageAdapter;

    public RecyclerView newProductHorizontalListRV,featuredProductHorizontalListRV;

    public static  ArrayList<Products> newProductsForHorizontalViewList;
    public static ArrayList<Products> featuredProductsForHorizontalViewList;
    public static ArrayList<Products> allProductsForGridViewList;

    private InternetConnection mInternetConnection;


    int offsetForNewProductsHorizontalScrolling = 0;
    int offsetForFeaturedProductsHorizontalScrolling = 0;
    int offsetForAllProductsInGridView = 0;
    int limit = 5;
    int limitForProductsInGridView = 10;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int pastVisibleItemsInGridView,visibleItemCountInGridView,totalItemCountInGridView;
    private boolean userScrolled;
    private boolean userScrolledInGridView;
    private boolean noMoreItem;
    private boolean noMoreItemInGridView;
    int lastlastitem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInternetConnection  = new InternetConnection(this);

        initialize();
        initializeSlider();

    }

    public void initialize()
    {
        newProductsForHorizontalViewList = new ArrayList<>();
        featuredProductsForHorizontalViewList = new ArrayList<>();
        allProductsForGridViewList = new ArrayList<>();

        this.userScrolled = true;
        this.noMoreItem = false;

        sliderShow = (SliderLayout) findViewById(R.id.slider);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newProductHorizontalListRV = (RecyclerView) findViewById(R.id.rv_horizontal);
        newProductHorizontalListRV.setLayoutManager(layoutManager);
        // Create adapter passing in the sample user data
        this.horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(this);
        // Attach the adapter to the recyclerview to populate items
        this.newProductHorizontalListRV.setAdapter(horizontalRecyclerViewAdapter);
        this.newProductHorizontalListRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        // Set layout manager to position the items
        if (mInternetConnection.isConnectingToInternet())
        {
            MainActivity.newProductsForHorizontalViewList.clear();
            new GetNewProductsAsyncTask(this).execute(
                    String.valueOf(offsetForNewProductsHorizontalScrolling),
                    String.valueOf(limit));
        }

        LinearLayoutManager layoutManagerForFeaturedProducts = new LinearLayoutManager(
                this,LinearLayoutManager.HORIZONTAL,false);
        featuredProductHorizontalListRV = (RecyclerView) findViewById(R.id.rv_featured_horizontalProducts);
        featuredProductHorizontalListRV.setLayoutManager(layoutManagerForFeaturedProducts);
        this.horizontalRVAFeaturedProductsAdapter = new HorizontalRVAFeaturedProductsAdapter(this);
        this.featuredProductHorizontalListRV.setAdapter(horizontalRVAFeaturedProductsAdapter);
        this.featuredProductHorizontalListRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        if (mInternetConnection.isConnectingToInternet())
        {
            MainActivity.newProductsForHorizontalViewList.clear();
            new GetFeaturedProductsAsyncTask(this).execute(
                    String.valueOf(offsetForNewProductsHorizontalScrolling),
                    String.valueOf(limit));
        }



    //TODO recyclerview onscroll load more
     /*   this.newProductHorizontalListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (userScrolled) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            userScrolled = false;
                            Log.v("...", "Last Item Wow !");
                            loadMore();
                        }
                    }
                }
            }
        });*/

        this.gridViewForAllProducts = (GridView) findViewById(R.id.gridView_all_Product);
        this.gridViewForAllProducts.setOnItemClickListener(this);
        this.gridViewForAllProducts.setOnScrollListener(this);

        this.gridViewProductsInHomePageAdapter = new GridViewProductsInHomePageAdapter(this);
        this.gridViewForAllProducts.setAdapter(gridViewProductsInHomePageAdapter);

        if (mInternetConnection.isConnectingToInternet())
        {
            MainActivity.allProductsForGridViewList.clear();
            new GetAllProductForGridViewAsyncTask(this).execute(String.valueOf(offsetForAllProductsInGridView),
                    String.valueOf(limitForProductsInGridView));
        }
    }

    private void loadMore() {
        if (mInternetConnection.isConnectingToInternet()) {
            offsetForNewProductsHorizontalScrolling += 1 ;
            System.out.println("I am going for loading more contents with offsetForNewProductsHorizontalScrolling:" + offsetForNewProductsHorizontalScrolling);
            new GetNewProductsAsyncTask(this).execute(String.valueOf(offsetForNewProductsHorizontalScrolling),String.valueOf(limit));

        }
    }

    public void initializeSlider()
    {
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

    public void setNewProductsList(ArrayList<Products> productsList) {
        System.out.println("productArrayListSize:" + productsList.size());

        for (int i = 0; i < productsList.size(); i++) {
            try {
                System.out.println("product id:"+productsList.get(i).id);
                MainActivity.newProductsForHorizontalViewList.add(productsList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + MainActivity.newProductsForHorizontalViewList.size());
      //  this.horizontalListViewOfProductsAdapter.notifyDataSetChanged();
        this.horizontalRecyclerViewAdapter.notifyDataSetChanged();

    }
    public void setNewProductListError() {

        userScrolled = false;
        noMoreItem = true;
        Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }

    public void setFeaturedProductsList(ArrayList<Products> productList) {

        System.out.println("productArrayListSize:" + productList.size());

        for (int i = 0; i < productList.size(); i++) {
            try {
                System.out.println("product id:"+productList.get(i).id);
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
        Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }

    public void setAllProductList(ArrayList<Products> productsList){
        System.out.print("allProductsListSize:" + productsList.size());

        for (int i = 0; i < productsList.size(); i++) {
            try {
                System.out.println("product id:"+productsList.get(i).id);
                MainActivity.allProductsForGridViewList.add(productsList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userScrolledInGridView = false;
        noMoreItemInGridView = false;
        System.out.println("Final Data Limit:" + MainActivity.allProductsForGridViewList.size());
        this.gridViewProductsInHomePageAdapter.notifyDataSetChanged();

    }

    public void setAllProductsListError(){
        userScrolledInGridView = false;
        noMoreItemInGridView = true;
        Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
            if(lastlastitem != lastInScreen){
                lastlastitem = lastInScreen;
                //  if (Utility.current_number < Utility.total) {
                if (mInternetConnection.isConnectingToInternet()) {
                    // search.offset = Utility.page_number;
                    offsetForAllProductsInGridView += 1 ;
                    System.out.println("I am going for loading more contents with offsett:" + offsetForAllProductsInGridView);
                    new GetAllProductForGridViewAsyncTask(this).execute(String.valueOf(offsetForAllProductsInGridView),
                            String.valueOf(limitForProductsInGridView));


                }
            }


        }
    }



}
