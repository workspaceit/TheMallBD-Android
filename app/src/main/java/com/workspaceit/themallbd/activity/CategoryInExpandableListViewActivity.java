package com.workspaceit.themallbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.CategoryInExpandableListViewAdapter;
import com.workspaceit.themallbd.adapter.CategoryInListViewAdapter;
import com.workspaceit.themallbd.asynctask.CategoryInExpandableListViewAsyncTask;
import com.workspaceit.themallbd.asynctask.CategoryInListViewAsyncTask;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.service.InternetConnection;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

public class CategoryInExpandableListViewActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener, ExpandableListView.OnChildClickListener {

    private ExpandableListView expandableListView;
    private ArrayList<Category> parentsWithChildrenArrayList;

    private CategoryInExpandableListViewAdapter categoryInExpandableListViewAdapter;
    private InternetConnection mInternetConnection;

    private int position = 0;
    private String title = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_in_expandable_list_view);

        position = getIntent().getIntExtra("position", -1);
        title = getIntent().getStringExtra("title");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

        mInternetConnection = new InternetConnection(this);

        expandableListView = (ExpandableListView) findViewById(R.id.category_expandable_list_view);
      //  expandableListView.setOnItemClickListener(this);

        this.parentsWithChildrenArrayList = new ArrayList<>();

        /*if (mInternetConnection.isConnectingToInternet())
        {
            new CategoryInExpandableListViewAsyncTask(this).execute();
        }*/

        categoryInExpandableListViewAdapter = new CategoryInExpandableListViewAdapter(CategoryInExpandableListViewActivity.this,
                Utility.parentsCategoryArraylist);
        expandableListView.setAdapter(categoryInExpandableListViewAdapter);

        //
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
