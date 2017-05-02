package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.activity.CategoryInExpandableListViewActivity;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.dataModel.Category;
import com.themallbd.workspaceit.service.CategoryService;

import java.util.ArrayList;

/**
 * Created by rajib on 2/29/16.
 */
public class CategoryInExpandableListViewAsyncTask extends AsyncTask<String,String,ArrayList<Category>> {
    private CategoryInExpandableListViewActivity mContext;
    private ProgressDialog mProgressDialog;

    public CategoryInExpandableListViewAsyncTask(CategoryInExpandableListViewActivity categoryActivity) {
        this.mContext = categoryActivity;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading categories");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Category> doInBackground(String... params) {

        CategoryService categoryService = new CategoryService();

        return categoryService.getParentsCategories();
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        super.onPostExecute(categories);
        mProgressDialog.dismiss();
        if (categories.size()>0)
            mContext.setNewCategoryArrayList(categories);
        else {
            if (!Utility.responseStat.status)
                mContext.setNewCategoryListError();
            else
                Toast.makeText(mContext, "Something Went wrong", Toast.LENGTH_SHORT).show();
        }



    }
}
