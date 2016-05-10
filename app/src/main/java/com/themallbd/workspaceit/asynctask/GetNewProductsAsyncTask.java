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
public class GetNewProductsAsyncTask extends AsyncTask<String,String,ArrayList<Products>> {

    private MainActivity mContext;
    //private ProgressDialog mProgressDialog;

    public GetNewProductsAsyncTask(MainActivity mContext) {
        this.mContext = mContext;
    }


    @Override
    protected void onPreExecute() {
      /*  mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Getting data...");
        mProgressDialog.show();*/
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        String offset = params[0];
        String limit = params[1];
        ProductService productService = new ProductService();
        return productService.getNewProducts(offset, limit);
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        //mProgressDialog.dismiss();
        if (productses.size()>0)
        {
            mContext.setNewProductsList(productses);
        }
        else {
            if (!Utility.responseStat.status)
                mContext.setNewProductListError();
            else
                Toast.makeText(mContext, "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
