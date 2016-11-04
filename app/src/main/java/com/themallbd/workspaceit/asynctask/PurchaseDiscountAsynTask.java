package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.fragment.CheckoutViewFragment;

/**
 * Created by Tomal on 11/4/2016.
 */

public class PurchaseDiscountAsynTask extends AsyncTask<String,String,Boolean> {
    private CheckoutViewFragment checkoutViewFragment;

    public PurchaseDiscountAsynTask(CheckoutViewFragment checkoutViewFragment){
        this.checkoutViewFragment=checkoutViewFragment;

    }

    @Override
    protected Boolean doInBackground(String... params) {
        return false;
    }


}
