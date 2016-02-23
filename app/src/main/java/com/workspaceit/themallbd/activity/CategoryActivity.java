package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.CategoryInGridViewAdapter;
import com.workspaceit.themallbd.asynctask.GetSubCategoryOfParentsAsyncTask;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.InternetConnection;
import com.workspaceit.themallbd.utility.RecyclerItemClickListener;

import java.util.ArrayList;

public class CategoryActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private InternetConnection mInternetConnection;

    private CategoryInGridViewAdapter categoryInGridViewAdapter;

    public static ArrayList<Category> newCategoryArrayList;

    public int parentId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mInternetConnection = new InternetConnection(this);

        parentId = getIntent().getIntExtra("parent_id", -1);
        initialize();


    }

    private void initialize() {

        CategoryActivity.newCategoryArrayList = new ArrayList<>();

        GridView gridCategories = (GridView) findViewById(R.id.gridView_category);
        gridCategories.setOnItemClickListener(this);

        this.categoryInGridViewAdapter = new CategoryInGridViewAdapter(this);
        gridCategories.setAdapter(categoryInGridViewAdapter);

        if (mInternetConnection.isConnectingToInternet())
        {
            CategoryActivity.newCategoryArrayList.clear();
            if (parentId>0)
                new GetSubCategoryOfParentsAsyncTask(this).execute(String.valueOf(parentId));
            else
                new GetSubCategoryOfParentsAsyncTask(this).execute(String.valueOf(9));
        }
    }

    public void setNewCategoryArrayList(ArrayList<Category> categoryList) {
        System.out.println("categoryList:" + categoryList.size());

        for (int i = 0; i < categoryList.size(); i++) {
            try {
                System.out.println("category id:"+categoryList.get(i).id);
                CategoryActivity.newCategoryArrayList.add(categoryList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + CategoryActivity.newCategoryArrayList.size());
        //  this.horizontalListViewOfProductsAdapter.notifyDataSetChanged();
        this.categoryInGridViewAdapter.notifyDataSetChanged();
    }

    public void setNewCategoryListError() {

        Toast.makeText(this, "No Data for new category", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }
}
