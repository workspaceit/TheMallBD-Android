package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.ProductService;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/15/16.
 */
public class GetFeaturedProductsAsyncTask extends AsyncTask<String,String,ArrayList<Products>> {

    private MainActivity mContext;


    public GetFeaturedProductsAsyncTask(MainActivity mContext) {
        this.mContext = mContext;
    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        String offset = params[0];
        String limit = params[1];
        ProductService productService = new ProductService();
        ArrayList<Products> productsArrayList = productService.getFeaturedProducts(offset, limit);
        return productsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);

        if (productses.size()>0)
        {
            mContext.setFeaturedProductsList(productses);
        }
        else {
            if (!Utility.responseStat.status)
                mContext.setFeaturedProductListError();
            else
                Toast.makeText(mContext, "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
