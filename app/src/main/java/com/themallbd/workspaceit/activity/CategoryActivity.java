package com.themallbd.workspaceit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.themallbd.workspaceit.adapter.CategoryInGridViewAdapter;
import com.themallbd.workspaceit.service.InternetConnection;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.asynctask.GetSubCategoryOfParentsAsyncTask;
import com.themallbd.workspaceit.dataModel.Category;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener,Serializable {

    private InternetConnection mInternetConnection;

    private CategoryInGridViewAdapter categoryInGridViewAdapter;

    private  GridView gridCategories;

    public static ArrayList<Category> newCategoryArrayList;

    public int parentId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mInternetConnection = new InternetConnection(this);

        parentId = getIntent().getIntExtra("parent_id", -1);

        CategoryActivity.newCategoryArrayList = new ArrayList<>();

        gridCategories = (GridView) findViewById(R.id.gridView_category);
        gridCategories.setOnItemClickListener(this);

        this.categoryInGridViewAdapter = new CategoryInGridViewAdapter(this);
        gridCategories.setAdapter(categoryInGridViewAdapter);

        initialize();

    }

    private void initialize() {
        if (mInternetConnection.checkInternet())
        {
            CategoryActivity.newCategoryArrayList.clear();
            new GetSubCategoryOfParentsAsyncTask(this).execute(String.valueOf(parentId));
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
