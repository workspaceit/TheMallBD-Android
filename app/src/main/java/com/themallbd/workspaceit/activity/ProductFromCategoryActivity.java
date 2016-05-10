package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.themallbd.workspaceit.asynctask.CategoryWiseProductsAsyncTask;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.InternetConnection;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.adapter.CategoryWiseProductsAdapter;

import java.util.ArrayList;

public class ProductFromCategoryActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private ListView productCategoryListView;

    private InternetConnection mInternetConnection;

    private CategoryWiseProductsAdapter categoryWiseProductsAdapter;

    public static ArrayList<Products> categoryWiseProductsArrayList;
    private int CATEGORY_ID=0;
    private int offset = 0,limit = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_from_category);

        CATEGORY_ID = getIntent().getIntExtra("category_id",-1);

        mInternetConnection = new InternetConnection(this);
        categoryWiseProductsArrayList = new ArrayList<>();

        initialize();
    }

    private void initialize() {

        productCategoryListView = (ListView) findViewById(R.id.product_category_listView);

        this.categoryWiseProductsAdapter = new CategoryWiseProductsAdapter(this);
        productCategoryListView.setAdapter(categoryWiseProductsAdapter);

        if (mInternetConnection.isConnectingToInternet())
        {

            ProductFromCategoryActivity.categoryWiseProductsArrayList.clear();
            new CategoryWiseProductsAsyncTask(this).execute(String.valueOf(offset),String.valueOf(limit)
            ,String.valueOf(CATEGORY_ID));

        }

        productCategoryListView.setOnItemClickListener(this);

    }

    public void setCategoryWiseProducts(ArrayList<Products> productses) {

        System.out.println("productArrayListSize:" + productses.size());

        for (int i = 0; i < productses.size(); i++) {
            try {
                System.out.println("product id:"+productses.get(i).id);
                ProductFromCategoryActivity.categoryWiseProductsArrayList.add(productses.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + ProductFromCategoryActivity.categoryWiseProductsArrayList.size());
        //  this.horizontalListViewOfProductsAdapter.notifyDataSetChanged();
        this.categoryWiseProductsAdapter.notifyDataSetChanged();
    }

    public void setNewProductListError() {
        Toast.makeText(this, "No Item Found!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",4);
        startActivity(intent);
    }
}
