package com.themallbd.workspaceit.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.service.WishListService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Mausum on 4/11/2016.
 */
public class WishListAsynTask extends AsyncTask<String,String,Boolean> {

    private Activity context;
    private ProgressDialog mProgressDialog;


    public WishListAsynTask(Activity activity){
        this.context=activity;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Adding Product to the wishlist...");
        mProgressDialog.show();
    }






    @Override
    protected Boolean doInBackground(String... params) {
        String customerId=params[0];
        String productId=params[1];
        WishListService wishListService=new WishListService();

        return wishListService.addProductToWisgList(customerId,productId);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if(aBoolean){
            MakeToast.showToast(context, "Product Added to the wishlist Succesfully");
        }else{
            if(Utility.responseStat.msg!=null){
                MakeToast.showToast(context,Utility.responseStat.msg);
            }else {
                MakeToast.showToast(context,"Something went wrong");
            }
        }

    }


}
