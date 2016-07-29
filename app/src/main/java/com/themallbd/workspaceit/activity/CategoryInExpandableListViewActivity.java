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

public class CategoryInExpandableListViewActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

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


        expandableListView = (ExpandableListView) findViewById(R.id.category_expandable_list_view);

        //  expandableListView.setOnItemClickListener(this);

        this.parentsWithChildrenArrayList = new ArrayList<>();



        categoryInExpandableListViewAdapter = new CategoryInExpandableListViewAdapter(
                CategoryInExpandableListViewActivity.this,
                Utility.parentsCategoryArraylist);
        expandableListView.setAdapter(categoryInExpandableListViewAdapter);


        expandableListView.expandGroup(position);


    }

    public void setNewCategoryListError() {

    }

    public void setNewCategoryArrayList(ArrayList<Category> categories) {


        for (int i = 0; i < categories.size(); i++) {
            try {
                if (!categories.get(i).childrens.isEmpty())
                    this.parentsWithChildrenArrayList.add(categories.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - GetPixelFromDips(35), width - GetPixelFromDips(5));
        } else {
            expandableListView.setIndicatorBoundsRelative(width - GetPixelFromDips(35), width - GetPixelFromDips(5));
        }
    }


}
