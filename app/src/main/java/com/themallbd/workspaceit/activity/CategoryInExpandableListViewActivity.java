package com.themallbd.workspaceit.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.adapter.CategoryInExpandableListViewAdapter;
import com.themallbd.workspaceit.dataModel.Category;

import java.util.ArrayList;

public class CategoryInExpandableListViewActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener, ExpandableListView.OnChildClickListener {

    private ExpandableListView expandableListView;
    private ArrayList<Category> parentsWithChildrenArrayList;

    private CategoryInExpandableListViewAdapter categoryInExpandableListViewAdapter;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_in_expandable_list_view);

        position = getIntent().getIntExtra("position", -1);
        String title = getIntent().getStringExtra("title");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        expandableListView = (ExpandableListView) findViewById(R.id.category_expandable_list_view);
      //  expandableListView.setOnItemClickListener(this);

        this.parentsWithChildrenArrayList = new ArrayList<>();

        /*if (mInternetConnection.isConnectingToInternet())
        {
            new CategoryInExpandableListViewAsyncTask(this).execute();
        }*/

        categoryInExpandableListViewAdapter = new CategoryInExpandableListViewAdapter(
                CategoryInExpandableListViewActivity.this,
                Utility.parentsCategoryArraylist);
        expandableListView.setAdapter(categoryInExpandableListViewAdapter);


        expandableListView.expandGroup(position);

        expandableListView.setOnChildClickListener(this);


    }

    public void setNewCategoryArrayList(ArrayList<Category> categories) {

        System.out.println("categoryList:" + categories.size());

        for (int i = 0; i < categories.size(); i++) {
            try {
                System.out.println("category id:"+categories.get(i).id);
                this.parentsWithChildrenArrayList.add(categories.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Data Limit:" + this.parentsWithChildrenArrayList.size());

        categoryInExpandableListViewAdapter = new CategoryInExpandableListViewAdapter(CategoryInExpandableListViewActivity.this,this.parentsWithChildrenArrayList);
        expandableListView.setAdapter(categoryInExpandableListViewAdapter);

        //
        expandableListView.expandGroup(position);
    }

    public void setNewCategoryListError() {
        Toast.makeText(this, "No Data for new category", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - GetPixelFromDips(35), width - GetPixelFromDips(5));
        } else {
            expandableListView.setIndicatorBoundsRelative(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(this,"asdda",Toast.LENGTH_LONG).show();
        return false;
    }
}
