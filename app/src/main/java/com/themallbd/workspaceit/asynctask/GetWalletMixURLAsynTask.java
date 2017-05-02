package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.WalletMixPaymentWebViewActivity;
import com.themallbd.workspaceit.service.PaymentService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 8/8/2016.
 */
public class GetWalletMixURLAsynTask extends AsyncTask<String,String,String> {

    private WalletMixPaymentWebViewActivity walletMixPaymentWebViewActivity;
    private ProgressDialog mProgressDialog;

    public GetWalletMixURLAsynTask(WalletMixPaymentWebViewActivity walletMixPaymentWebViewActivity){
        this.walletMixPaymentWebViewActivity=walletMixPaymentWebViewActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.walletMixPaymentWebViewActivity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Connecting to Payment Gateway. Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }



    @Override
    protected String doInBackground(String... params) {
        String orderId=params[0];
        return new PaymentService().getWalletMixConfrimation(orderId);
    }


    @Override
    protected void onPostExecute(String s) {
        mProgressDialog.dismiss();
        if (Utility.responseStat.status){
            walletMixPaymentWebViewActivity.setupWebView(s);
        }else {
            MakeToast.showToast(walletMixPaymentWebViewActivity,"Something went wrong..");
            walletMixPaymentWebViewActivity.finish();
        }
        super.onPostExecute(s);
    }
}
