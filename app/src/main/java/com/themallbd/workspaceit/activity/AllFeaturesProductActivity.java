package com.themallbd.workspaceit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.AnyProductListAdapter;
import com.themallbd.workspaceit.asynctask.GetFeaturedProductsAsyncTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class AllFeaturesProductActivity extends BaseActivityWithoutDrawer implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private ListView allFeatureProductListView;
    private AnyProductListAdapter anyProductListAdapter;
    private int limit=5;
    private int offset=0;
    private InternetConnection mInternetConnection;
    private boolean loadProductFlag=true;
    private View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_features_product);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        allFeatureProductListView=(ListView)findViewById(R.id.all_feature_product_list_view);
        anyProductListAdapter=new AnyProductListAdapter(this,MainActivity.featuredProductsForHorizontalViewList);

        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.page_list_view_loader, null, false);
        allFeatureProductListView.addFooterView(footer);
        allFeatureProductListView.setAdapter(anyProductListAdapter);
        this.footer.setVisibility(View.GONE);
        mInternetConnection=new InternetConnection(this);
        this.offset=((MainActivity.featuredProductsForHorizontalViewList.size()/5)-1);




        this.allFeatureProductListView.setOnScrollListener(this);
        this.allFeatureProductListView.setOnItemClickListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount>=totalItemCount && loadProductFlag && MainActivity.moreItemFeatureProduct){


            loadProductFlag=false;
            if (mInternetConnection.checkInternet())
            {
                this.footer.setVisibility(View.VISIBLE);
                this.offset++;

                System.out.println("before call : "+this.offset+" limit "+this.limit+" size "+MainActivity.packgeProductForHorizontalList.size());

                new GetFeaturedProductsAsyncTask(this).execute(String.valueOf(this.offset), String.valueOf(limit));



            }else {
                Intent intent=new Intent(this,NoInternetActiviy.class);
                startActivity(intent);
                finish();
            }

        }

    }

    public void notifyDataSetChange() {
        this.footer.setVisibility(View.GONE);
        this.allFeatureProductListView.removeFooterView(footer);

        this.anyProductListAdapter.updateProductArrayList(MainActivity.featuredProductsForHorizontalViewList);
        this.anyProductListAdapter.notifyDataSetChanged();
        loadProductFlag=true;
    }

    public void featureProductError(){
       this.allFeatureProductListView.removeFooterView(footer);
        loadProductFlag=false;
        MainActivity.moreItemFeatureProduct=false;
        MakeToast.showToast(this,"No More Feauture Product...");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",2);
        startActivity(intent);


    }
}
