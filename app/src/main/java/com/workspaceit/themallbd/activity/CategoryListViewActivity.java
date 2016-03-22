package com.workspaceit.themallbd.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.CategoryInListViewAdapter;
import com.workspaceit.themallbd.asynctask.CategoryInListViewAsyncTask;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.service.InternetConnection;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

public class CategoryListViewActivity extends BaseActivityWithoutDrawer implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView categoryParentsListView;

    private CategoryInListViewAdapter categoryInListViewAdapter;

    private InternetConnection mInternetConnection;

    public ArrayList<Category> parentCategoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_view);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categories");

        mInternetConnection = new InternetConnection(this);

        categoryParentsListView = (ListView) findViewById(R.id.category_parents);
        categoryParentsListView.setOnItemClickListener(this);

        this.parentCategoryArrayList = new ArrayList<>();

        if (Utility.parentsCategoryArraylist.size()>0)
        {
            for (int i = 0; i < Utility.parentsCategoryArraylist.size(); i++) {
                try {
                    this.parentCategoryArrayList.add(Utility.parentsCategoryArraylist.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            setParentCategoryArrayList();
        }
        else {
            if (mInternetConnection.isConnectingToInternet()) {
                new CategoryInListViewAsyncTask(this).execute();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,CategoryInExpandableListViewActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("title",this.parentCategoryArrayList.get(position).title);
        startActivity(intent);
    }

    public void setParentCategoryArrayList(){
        categoryInListViewAdapter = new CategoryInListViewAdapter(this,Utility.parentsCategoryArraylist);
        categoryParentsListView.setAdapter(categoryInListViewAdapter);
    }
    public void setNewCategoryArrayList(ArrayList<Category> categoryList)
    {
        System.out.println("categoryList:" + categoryList.size());

        for (int i = 0; i < categoryList.size(); i++) {
            try {
                System.out.println("category id:"+categoryList.get(i).id);
                this.parentCategoryArrayList.add(categoryList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + this.parentCategoryArrayList.size());

        categoryInListViewAdapter = new CategoryInListViewAdapter(this,this.parentCategoryArrayList);
        categoryParentsListView.setAdapter(categoryInListViewAdapter);
    }

    public void setNewCategoryListError() {


    }
}
