package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.fragment.PaymentFragment;
import com.themallbd.workspaceit.service.GetPaymentAndDeliveyMethodsService;

/**
 * Created by Tomal on 8/1/2016.
 */
public class GetAllPaymentMethodAsynTask extends AsyncTask<String,String,Boolean> {

private PaymentFragment paymentFragment;
    public GetAllPaymentMethodAsynTask(PaymentFragment paymentFragment){
        this.paymentFragment=paymentFragment;
    }


    @Override
    protected Boolean doInBackground(String... params) {
        return new GetPaymentAndDeliveyMethodsService().getPaymentMethods();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean){
            paymentFragment.setRadioButton();
        }
    }
}
