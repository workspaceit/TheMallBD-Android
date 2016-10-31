package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.fragment.CheckoutViewFragment;
import com.themallbd.workspaceit.service.GetPaymentAndDeliveyMethods;

/**
 * Created by Tomal on 8/1/2016.
 */
public class GetAllDeliveryMethodsAsyncTask extends AsyncTask<String,String,Boolean> {

    private CheckoutViewFragment checkoutViewFragment;


    public GetAllDeliveryMethodsAsyncTask(CheckoutViewFragment checkoutViewFragment){
        this.checkoutViewFragment=checkoutViewFragment;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... params) {
        return new GetPaymentAndDeliveyMethods().getDeliveryMrthod();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean){
            checkoutViewFragment.setData();
        }
    }
}
