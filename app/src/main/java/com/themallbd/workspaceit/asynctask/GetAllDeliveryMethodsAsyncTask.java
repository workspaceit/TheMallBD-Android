package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.service.GetPaymentAndDeliveyMethods;

/**
 * Created by Tomal on 8/1/2016.
 */
public class GetAllDeliveryMethodsAsyncTask extends AsyncTask<String,String,Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        return new GetPaymentAndDeliveyMethods().getDeliveryMrthod();
    }
}
