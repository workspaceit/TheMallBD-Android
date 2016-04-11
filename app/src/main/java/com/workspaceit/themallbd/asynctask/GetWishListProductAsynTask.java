package com.workspaceit.themallbd.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.workspaceit.themallbd.activity.WishListActivity;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.WishListService;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/11/2016.
 */
public class GetWishListProductAsynTask extends AsyncTask<String,String,ArrayList<Products>> {
    private WishListActivity context;
    private ProgressDialog mProgressDialog;

    public GetWishListProductAsynTask(WishListActivity activity){
        this.context=activity;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Getting wishlist...");
        mProgressDialog.show();
    }




    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        WishListService wishListService=new WishListService();
        return wishListService.getProductOfWishList();
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        mProgressDialog.dismiss();
        if(productses.size()>0){
                context.changeAdapterState();
        }
    }
}
