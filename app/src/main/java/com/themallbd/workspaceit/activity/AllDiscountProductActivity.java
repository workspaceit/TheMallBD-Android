package com.themallbd.workspaceit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.AnyProductListAdapter;
import com.themallbd.workspaceit.asynctask.GetSpecailDiscountProductAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class AllDiscountProductActivity extends BaseActivityWithoutDrawer implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private GridView allDiscountProductGridView;
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

        try {
            toolbar=(Toolbar)findViewById(R.id.toolbar);

            allDiscountProductGridView =(GridView)findViewById(R.id.all_discount_product_grid_view);
            anyProductListAdapter=new AnyProductListAdapter(this,MainActivity.discountProductForHorizontalList);
            footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.page_list_view_loader, null, false);

            allDiscountProductGridView.setAdapter(anyProductListAdapter);
            this.footer.setVisibility(View.GONE);
            mInternetConnection=new InternetConnection(this);
            this.offset=((MainActivity.newProductsForHorizontalViewList.size()/5)-1);
            if (this.offset<0)
                this.offset=0;



            this.allDiscountProductGridView.setOnScrollListener(this);
            this.allDiscountProductGridView.setOnItemClickListener(this);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("productArray", 9);
            startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {



        if(firstVisibleItem+visibleItemCount>=totalItemCount && loadProductFlag && MainActivity.moreItemNewProduct){


            loadProductFlag=false;
            if (mInternetConnection.checkInternet())
            {
                this.footer.setVisibility(View.VISIBLE);
                this.offset++;


                new GetSpecailDiscountProductAsynTask(this).execute(String.valueOf(this.limit), String.valueOf(offset));



            }else {
                Intent intent=new Intent(this,NoInternetActiviy.class);
                startActivity(intent);
                finish();
            }

        }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void notifyDataSetChange() {
        this.footer.setVisibility(View.GONE);
        this.anyProductListAdapter.updateProductArrayList(MainActivity.discountProductForHorizontalList);
        this.anyProductListAdapter.notifyDataSetChanged();
        loadProductFlag=true;
    }

    public void newProductError(){
        loadProductFlag=false;
        MainActivity.moreDiscountProduct=false;

    }
}
