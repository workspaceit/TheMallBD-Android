package com.themallbd.workspaceit.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.service.PaymentService;

/**
 * Created by Tomal on 8/9/2016.
 */
public class CancelOrderAyncTask extends AsyncTask<String,String,Boolean> {



    @Override
    protected Boolean doInBackground(String... params) {
        String orderId=params[0];
        return new PaymentService().cancelOrder(orderId);
    }
}
