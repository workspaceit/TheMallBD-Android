package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;


import com.google.gson.Gson;
import com.themallbd.workspaceit.activity.CheckoutActivity;

import com.themallbd.workspaceit.fragment.PaymentFragment;
import com.themallbd.workspaceit.service.SubmitCheckoutService;
import com.themallbd.workspaceit.utility.CustomDialog;

import com.themallbd.workspaceit.utility.LocalShoppintCart;

import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 7/28/2016.
 */
public class SubmitChectoutAsyntask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private ProgressDialog mProgressDialog;

    public SubmitChectoutAsyntask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Completing Your Order. Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String firstName = params[0];
        String lastName = params[1];
        String email = params[2];
        String phone = params[3];
        String address = params[4];
        String city = params[5];
        String orderFrom = params[6];
        String shipping_address = params[7];
        String shipping_country = params[8];
        String shipping_zipcode = params[9];
        String shipping_city = params[10];
        String delivery_method_id = params[11];
        String payment_method_id = params[12];
        String currency_id = params[13];
        String shopping_cart = params[14];

        return new SubmitCheckoutService().submitCheckout(firstName, lastName, email, phone, address, city, orderFrom, shipping_address,
                shipping_country, shipping_zipcode, shipping_city, delivery_method_id, payment_method_id, currency_id, shopping_cart);

    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        try {


            if (aBoolean) {




                if (PaymentFragment.PAYMENT_ID==1){
                    Utility.resetShoppingCart();
                    Gson gson = new Gson();
                    String cartProdut = gson.toJson(Utility.shoppingCart.productCell);
                    String cartPackage = gson.toJson(Utility.shoppingCart.mallBdPackageCell);
                    LocalShoppintCart localShoppintCart = new LocalShoppintCart(context);
                    localShoppintCart.setProductCart(cartProdut);
                    localShoppintCart.setPackageCart(cartPackage);
                    CheckoutActivity.tabFlag=0;
                    if (Utility.isLoggedInFlag) {

                        CustomDialog.orderPlaceDiolog(context, "Thanks for shopping with us", "Your order has been placed successfully");
                    }else {
                        CustomDialog.orderPlaceWithoutLogin(context,"Thanks for shopping with us","Your order has been placed successfully");
                    }

                }else if (PaymentFragment.PAYMENT_ID==2){
                    CustomDialog.paypalPayment(context,"Paypal","Go to paypal payment page",Utility.finishOrderSummary.order_id);
                }else if (PaymentFragment.PAYMENT_ID==3){
                    MakeToast.showToast(context,"Bkash");
                }else if(PaymentFragment.PAYMENT_ID==4){
                    CustomDialog.walletMixPayment(context,"Payment","Now You will redirect to payment gateway",Utility.finishOrderSummary.order_id);


                }


            } else {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
                alertDialogBuilder.setTitle("Order Place");
                alertDialogBuilder
                        .setMessage(Utility.responseStat.msg)
                        .setCancelable(false)
                        .setPositiveButton("Go To shopping Cart", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                CheckoutActivity.tabFlag = 0;
                                CheckoutActivity.mViewPager.setCurrentItem(0);


                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();


                alertDialog.show();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
