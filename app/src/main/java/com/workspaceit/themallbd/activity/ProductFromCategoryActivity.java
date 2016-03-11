package com.workspaceit.themallbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.CategoryWiseProductsAdapter;
import com.workspaceit.themallbd.asynctask.CategoryWiseProductsAsyncTask;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.InternetConnection;

import java.util.ArrayList;

public class ProductFromCategoryActivity extends BaseActivityWithoutDrawer {

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
        Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show();
    }
}
