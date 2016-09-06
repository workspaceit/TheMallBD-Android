package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.themallbd.workspaceit.activity.BKashPaymentActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.activity.PrevoiusOrderActivity;
import com.themallbd.workspaceit.service.PaymentService;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 8/9/2016.
 */
public class ConfrimaBKashPaymentAsyncTask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private ProgressDialog mProgressDialog;

    public ConfrimaBKashPaymentAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Verifying Your Bkash Payment...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String uniqueCode = params[0];
        String phoneNumber = params[1];
        String trxId = params[2];

        return new PaymentService().confirmBkashpayment(uniqueCode, phoneNumber, trxId);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
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
                            if (context instanceof BKashPaymentActivity){
                                ((BKashPaymentActivity)context).finish();
                            }


                        }
                    }).setNegativeButton("Previous Order", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, PrevoiusOrderActivity.class);
                    context.startActivity(intent);
                }
            });

            android.app.AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();

        } else {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
            alertDialogBuilder.setTitle("Bkash Payment");
            alertDialogBuilder
                    .setMessage(Utility.responseStat.msg)
                    .setCancelable(false)
                    .setPositiveButton("Go T", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (context instanceof BKashPaymentActivity){
                                ((BKashPaymentActivity)context).finish();
                            }


                        }
                    }).setNegativeButton("Stay Here", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.cancel();
                }
            });

            android.app.AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();
        }

    }
}
