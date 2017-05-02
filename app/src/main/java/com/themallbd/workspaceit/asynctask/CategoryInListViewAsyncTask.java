package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.dataModel.Category;
import com.themallbd.workspaceit.service.CategoryService;

import java.util.ArrayList;

/**
 * Created by rajib on 2/26/16.
 */
public class CategoryInListViewAsyncTask extends AsyncTask<String,String,ArrayList<Category>> {
    private MainActivity mContext;
    private ProgressDialog mProgressDialog;

    public CategoryInListViewAsyncTask(MainActivity mainActivity) {
        this.mContext = mainActivity;
    }

    @Override
    protected void onPreExecute() {
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

        if (categories.size()>0) {
            mContext.initializeCategoryView(0);
        }
        else {
            if (!Utility.responseStat.status) {
                //mContext.setNewCategoryListError();
            }
            else {
                Toast.makeText(mContext, "Something Went wrong", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
