package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.fragment.CheckoutViewFragment;
import com.themallbd.workspaceit.service.GetPaymentAndDeliveyMethodsService;

/**
 * Created by Tomal on 8/1/2016.
 */
public class GetAllDeliveryMethodsAsyncTask extends AsyncTask<String,String,Boolean> {

    private CheckoutViewFragment checkoutViewFragment;
    private ProgressDialog mProgressDialog;


    public GetAllDeliveryMethodsAsyncTask(CheckoutViewFragment checkoutViewFragment){
        this.checkoutViewFragment=checkoutViewFragment;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(checkoutViewFragment.getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Getting Delivery Method...");
        mProgressDialog.show();


    }

    @Override
    protected Boolean doInBackground(String... params) {
        return new GetPaymentAndDeliveyMethodsService().getDeliveryMrthod();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
            mProgressDialog.dismiss();
        if (aBoolean){
            checkoutViewFragment.setData();
        }
    }
}
