package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.themallbd.workspaceit.adapter.AnyProductListAdapter;
import com.themallbd.workspaceit.asynctask.GetProductByManufracturerIdAsynTask;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

import java.util.ArrayList;

public class ProductByManufracturerActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private int manufracturerId = 0;
    private AnyProductListAdapter anyProductListAdapter;
    public static ArrayList<Products> productListByMenufracturer = new ArrayList<>();
    private InternetConnection internetConnection;
    private GridView productByManufracturerGridView;
    private int limit = 6;
    private int offset = 0;
    private boolean moreProduct = true;
    private boolean loadProductFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_manufracturer);

        productByManufracturerGridView = (GridView) findViewById(R.id.product_by_manufracturer_grid_view);
        productByManufracturerGridView.setOnItemClickListener(this);
        internetConnection = new InternetConnection(this);
        manufracturerId = getIntent().getIntExtra("manufracturer_id", 0);
        anyProductListAdapter = new AnyProductListAdapter(this, productListByMenufracturer);
        productByManufracturerGridView.setAdapter(anyProductListAdapter);


        if (internetConnection.checkInternet()) {
            productListByMenufracturer.clear();
            loadProductFlag = false;
            new GetProductByManufracturerIdAsynTask(this).execute(String.valueOf(manufracturerId), String.valueOf(limit), String.valueOf(offset));
        } else {
            Intent intent = new Intent(this, NoInternetActiviy.class);
            startActivity(intent);
            finish();
        }

        productByManufracturerGridView.setOnScrollListener(this);
    }

    public void MakeAdapterWork() {


        anyProductListAdapter.updateProductArrayList(productListByMenufracturer);
        anyProductListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == productByManufracturerGridView) {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("productArray", 11);
            startActivity(intent);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount >= totalItemCount && loadProductFlag && moreProduct) {


            loadProductFlag = false;
            if (internetConnection.checkInternet()) {


                this.offset++;


                new GetProductByManufracturerIdAsynTask(this).execute(String.valueOf(manufracturerId), String.valueOf(this.limit), String.valueOf(this.offset));


            } else {
                Intent intent = new Intent(this, NoInternetActiviy.class);
                startActivity(intent);
                finish();
            }

        }
    }

    public void setError() {
        moreProduct = false;
        MakeToast.showToast(this, "No more");
    }
}
