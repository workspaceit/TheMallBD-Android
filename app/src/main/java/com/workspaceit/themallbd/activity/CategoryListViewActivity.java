package com.workspaceit.themallbd.activity;

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

import java.util.ArrayList;

public class CategoryListViewActivity extends BaseActivityWithoutDrawer implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView categoryParentsListView;

    private CategoryInListViewAdapter categoryInListViewAdapter;

    private InternetConnection mInternetConnection;

    public ArrayList<Category> parentCategoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_view);
        mInternetConnection = new InternetConnection(this);

        categoryParentsListView = (ListView) findViewById(R.id.category_parents);
        categoryParentsListView.setOnItemClickListener(this);

        this.parentCategoryArrayList = new ArrayList<>();

        if (mInternetConnection.isConnectingToInternet())
        {
            new CategoryInListViewAsyncTask(this).execute();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
