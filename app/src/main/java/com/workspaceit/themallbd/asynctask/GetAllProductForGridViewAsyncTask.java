package com.workspaceit.themallbd.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.ProductService;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/15/16.
 */
public class GetAllProductForGridViewAsyncTask extends AsyncTask<String,String,ArrayList<Products>> {
    private MainActivity mContext;
    private ProgressDialog mProgressDialog;

    public GetAllProductForGridViewAsyncTask(MainActivity mContext) {
        this.mContext = mContext;
    }


    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Getting products...");
        mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        String offset = params[0];
        String limit = params[1];
        ProductService productService = new ProductService();
        ArrayList<Products> productsArrayList = productService.getNewProducts(offset,limit);
        return productsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        mProgressDialog.dismiss();
        if (productses.size()>0)
        {
            mContext.setAllProductList(productses);
        }
        else {
            if (!Utility.responseStat.status)
                mContext.setAllProductsListError();
            else
                Toast.makeText(mContext, "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
