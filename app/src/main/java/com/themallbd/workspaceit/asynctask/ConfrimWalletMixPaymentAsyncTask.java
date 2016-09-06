package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.themallbd.workspaceit.activity.CheckoutActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.activity.WalletMixPaymentWebViewActivity;
import com.themallbd.workspaceit.service.PaymentService;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 8/9/2016.
 */
public class ConfrimWalletMixPaymentAsyncTask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private ProgressDialog mProgressDialog;


    public ConfrimWalletMixPaymentAsyncTask(Context context) {
        this.context = context;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Checking your payment status.....");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String orderId = params[0];
        return new PaymentService().confrimWalletMixPayment(orderId);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            Utility.resetShoppingCart();
            Gson gson = new Gson();
            String cartProdut = gson.toJson(Utility.shoppingCart.productCell);
            final String cartPackage = gson.toJson(Utility.shoppingCart.mallBdPackageCell);
            LocalShoppintCart localShoppintCart = new LocalShoppintCart(context);
            localShoppintCart.setProductCart(cartProdut);
            localShoppintCart.setPackageCart(cartPackage);

            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
            alertDialogBuilder.setTitle("Bkash payment succes");
            alertDialogBuilder
                    .setMessage("Thanks For shoppimg with The Mall BD")
                    .setCancelable(false)
                    .setPositiveButton("Continue Shopping", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);

                        }
                    }).setNegativeButton("Previous Order", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   if (context instanceof WalletMixPaymentWebViewActivity){
                       ((WalletMixPaymentWebViewActivity)context).finish();
                   }
                }
            });

            android.app.AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();

        } else {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
            alertDialogBuilder.setTitle("WalletMix Payment Failed");
            alertDialogBuilder
                    .setMessage(Utility.responseStat.msg)
                    .setCancelable(false)
                    .setPositiveButton("Go to cart", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, CheckoutActivity.class);
                            context.startActivity(intent);
                            if (context instanceof WalletMixPaymentWebViewActivity) {
                                ((WalletMixPaymentWebViewActivity)context).finish();
                            }


                        }
                    });
            android.app.AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();
        }
    }


}
