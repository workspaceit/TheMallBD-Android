package com.workspaceit.themallbd.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.workspaceit.themallbd.activity.CategoryActivity;
import com.workspaceit.themallbd.activity.CategoryListViewActivity;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.service.CategoryService;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/26/16.
 */
public class CategoryInListViewAsyncTask extends AsyncTask<String,String,ArrayList<Category>> {
    private CategoryListViewActivity mContext;
    private ProgressDialog mProgressDialog;

    public CategoryInListViewAsyncTask(CategoryListViewActivity categoryActivity) {
        this.mContext = categoryActivity;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading categories");
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
