package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.WishListActivity;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.WishListService;
import com.themallbd.workspaceit.utility.MakeToast;

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
        }else {
            MakeToast.showToast(context, "Currently there is no product in your Wishlist");
        }
    }
}
