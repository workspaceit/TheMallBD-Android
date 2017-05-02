package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.activity.ProductFromCategoryActivity;
import com.themallbd.workspaceit.service.ProductService;

import java.util.ArrayList;

/**
 * Created by rajib on 3/11/16.
 */
public class CategoryWiseProductsAsyncTask extends AsyncTask<String,String,ArrayList<Products>> {

    private ProductFromCategoryActivity activity;
    private ProgressDialog mProgressDialog;

    public CategoryWiseProductsAsyncTask(ProductFromCategoryActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        String offset = params[0];
        String limit = params[1];
        String categoryId = params[2];
        ProductService productService = new ProductService();
        return productService.getCategoryWiseProducts(offset,limit,categoryId);
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        mProgressDialog.dismiss();
        if (productses.size()>0)
        {
            activity.setCategoryWiseProducts(productses);
        }
        else {
            if (!Utility.responseStat.status) {
                activity.setNewProductListError();
            }
            else {
                Toast.makeText(activity, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
