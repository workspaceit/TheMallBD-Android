package com.workspaceit.themallbd.asynctask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.workspaceit.themallbd.activity.CategoryActivity;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.service.CategoryService;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/23/16.
 */
public class GetSubCategoryOfParentsAsyncTask extends AsyncTask<String, String, ArrayList<Category>> {

    private CategoryActivity mContext;

    public GetSubCategoryOfParentsAsyncTask(CategoryActivity categoryActivity) {
        this.mContext = categoryActivity;
    }

    @Override
    protected ArrayList<Category> doInBackground(String... params) {

        String parentId = params[0];
        CategoryService categoryService = new CategoryService();

        return categoryService.getChildCategoryByParentId(parentId);
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        super.onPostExecute(categories);
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
