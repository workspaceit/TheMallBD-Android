package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.ProductDetailsActivity;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.GetSearchProductService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 7/19/2016.
 */
public class GetProductByProductIdAsynctask extends AsyncTask<String,String,Products> {

    private ProductDetailsActivity activity;
    private ProgressDialog mProgressDialog;

    public GetProductByProductIdAsynctask(ProductDetailsActivity activity){
        this.activity=activity;

    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(this.activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading Product....");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        super.onPreExecute();
    }



    @Override
    protected Products doInBackground(String... params) {
        String productId=params[0];
        return new GetSearchProductService().getProductByProductId(productId);
    }

    @Override
    protected void onPostExecute(Products products) {
        super.onPostExecute(products);
        mProgressDialog.dismiss();
        if(products==null){
            MakeToast.showToast(activity,"Something went wrong...");
        }else {
            activity.setProductForSearch(products);
        }




    }
}
