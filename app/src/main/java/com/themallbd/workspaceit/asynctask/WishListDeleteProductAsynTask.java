package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.WishListActivity;
import com.themallbd.workspaceit.service.WishListService;

/**
 * Created by Tomal on 11/3/2016.
 */

public class WishListDeleteProductAsynTask extends AsyncTask<String,String,Boolean> {
    private WishListActivity wishListActivity;
    private ProgressDialog mProgressDialog;

    public WishListDeleteProductAsynTask(WishListActivity wishListActivity){
        this.wishListActivity=wishListActivity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(wishListActivity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Deleting Product from your wishlist...");
        mProgressDialog.show();
    }




    @Override
    protected Boolean doInBackground(String... params) {
        String productId=params[0];
        return new WishListService().deleteProductFromWishList(productId);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        wishListActivity.deleteProductNotify(aBoolean);
    }

}
