package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;


import com.google.gson.Gson;
import com.themallbd.workspaceit.activity.CheckoutActivity;

import com.themallbd.workspaceit.fragment.CheckoutViewFragment;
import com.themallbd.workspaceit.fragment.PaymentFragment;
import com.themallbd.workspaceit.service.SubmitCheckoutService;
import com.themallbd.workspaceit.utility.CustomDialog;

import com.themallbd.workspaceit.preferences.LocalShoppintCart;

import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 7/28/2016.
 */
public class SubmitChectoutAsyntask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private PaymentFragment paymentFragment;
    private ProgressDialog mProgressDialog;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;

    private String orderFrom;
    private String invoice_address;

    private String shipping_firstname;
    private String shipping_lastname;
    private String shipping_phone;
    private String shipping_address;
    private String shipping_city;

    private String delivery_method_id;
    private String payment_method_id;
    private String currency_id;

    private String voucherDiscountDetails;
    private String customerDiscount;
    private String customerDiscountDetails;

    private String Shopping_cart;


    public SubmitChectoutAsyntask(PaymentFragment paymentFragment,Context context,String firstName,String lastName,String email,String phone,String address,
                                  String city,String orderFrom, String invoice_address,String shipping_firstname,String shipping_lastname,
                                  String shipping_phone,String shipping_address,String shipping_city,String delivery_method_id,
                                  String payment_method_id,String currency_id,String voucherDiscountDetails,String customerDiscount,
                                  String customerDiscountDetails,String Shopping_cart) {
        this.paymentFragment=paymentFragment;
        this.context = context;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.city=city;
        this.orderFrom=orderFrom;
        this.invoice_address=invoice_address;
        this.shipping_firstname=shipping_firstname;
        this.shipping_lastname=shipping_lastname;
        this.shipping_phone=shipping_phone;
        this.shipping_address=shipping_address;
        this.shipping_city=shipping_city;
        this.delivery_method_id=delivery_method_id;
        this.payment_method_id=payment_method_id;
        this.currency_id=currency_id;
        this.voucherDiscountDetails=voucherDiscountDetails;
        this.customerDiscount=customerDiscount;
        this.customerDiscountDetails=customerDiscountDetails;
        this.Shopping_cart=Shopping_cart;
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



        return new SubmitCheckoutService().submitCheckout(this.firstName,this.lastName,this.email,this.phone,this.address,this.city,
                this.orderFrom,this.invoice_address,this.shipping_firstname,this.shipping_lastname,this.shipping_phone,
                this.shipping_address,this.shipping_city,this.delivery_method_id,this.payment_method_id,this.currency_id,
                this.voucherDiscountDetails,this.customerDiscount,this.customerDiscountDetails,this.Shopping_cart);

    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();


        try {


            if (aBoolean) {


                if (PaymentFragment.PAYMENT_ID == 1) {
                    Utility.resetShoppingCart();
                    Gson gson = new Gson();
                    String cartProdut = gson.toJson(Utility.shoppingCart.productCell);
                    String cartPackage = gson.toJson(Utility.shoppingCart.mallBdPackageCell);
                    LocalShoppintCart localShoppintCart = new LocalShoppintCart(context);
                    localShoppintCart.setProductCart(cartProdut);
                    localShoppintCart.setPackageCart(cartPackage);
                    CheckoutActivity.tabFlag = 0;

                    CheckoutViewFragment.dicount_message="";
                    CheckoutViewFragment.discountSuccesFlag=false;

                    if (Utility.isLoggedInFlag) {

                        CustomDialog.orderPlaceDiolog(context, "Thanks for shopping with us", "Your order has been placed successfully");
                    } else {
                        CustomDialog.orderPlaceWithoutLogin(context, "Thanks for shopping with us", "Your order has been placed successfully");
                    }

                } else if (PaymentFragment.PAYMENT_ID == 2) {
                    CustomDialog.BkashPaymentDialog(context, "Bkash", "Go to Bkash Payment page", Utility.finishOrderSummary.unique_code);
                } else if (PaymentFragment.PAYMENT_ID == 3) {
                    CustomDialog.paypalPayment(context, "Paypal", "Go to paypal payment page");

                } else if (PaymentFragment.PAYMENT_ID == 4) {
                    CustomDialog.walletMixPayment(context, "Payment", "Now You will redirect to payment gateway", Utility.finishOrderSummary.order_id);


                }


            } else {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
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
