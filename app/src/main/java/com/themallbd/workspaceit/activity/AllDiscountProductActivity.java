package com.themallbd.workspaceit.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.AnyProductListAdapter;
import com.themallbd.workspaceit.asynctask.GetNewProductsAsyncTask;
import com.themallbd.workspaceit.asynctask.GetSpecailDiscountProductAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class AllDiscountProductActivity extends BaseActivityWithoutDrawer implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private ListView allDiscountProductListView;
    private AnyProductListAdapter anyProductListAdapter;
    private int limit=5;
    private int offset=0;
    private InternetConnection mInternetConnection;
    private boolean loadProductFlag=true;
    private View footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_discount_product);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolBarTitle=(TextView)toolbar.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Special Discount Product");
        allDiscountProductListView=(ListView)findViewById(R.id.all_discount_product_list_view);
        anyProductListAdapter=new AnyProductListAdapter(this,MainActivity.discountProductForHorizontalList);
        allDiscountProductListView.setAdapter(anyProductListAdapter);

        mInternetConnection=new InternetConnection(this);
        this.offset=((MainActivity.newProductsForHorizontalViewList.size()/5)-1);


        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.page_list_view_loader, null, false);

        this.allDiscountProductListView.setOnScrollListener(this);
        this.allDiscountProductListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("productArray", 9);
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount>=totalItemCount && loadProductFlag && MainActivity.moreItemNewProduct){


            loadProductFlag=false;
            if (mInternetConnection.isConnectingToInternet())
            {
                allDiscountProductListView.addFooterView(footer);
                this.offset++;


                new GetSpecailDiscountProductAsynTask(this).execute(String.valueOf(this.limit), String.valueOf(offset));



            }else {
                Intent intent=new Intent(this,NoInternetActiviy.class);
                startActivity(intent);
                finish();
            }

        }

    }

    public void notifyDataSetChange() {
        this.allDiscountProductListView.removeFooterView(footer);
        this.anyProductListAdapter.updateProductArrayList(MainActivity.discountProductForHorizontalList);
        this.anyProductListAdapter.notifyDataSetChanged();
        loadProductFlag=true;
    }

    public void newProductError(){
        this.allDiscountProductListView.removeFooterView(footer);
        loadProductFlag=false;
        MainActivity.moreDiscountProduct=false;
        MakeToast.showToast(this, "No More New Product...");
    }
}
