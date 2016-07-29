package com.themallbd.workspaceit.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.activity.ProductDetailsActivity;
import com.themallbd.workspaceit.service.SubmitCheckoutService;
import com.themallbd.workspaceit.utility.MakeToast;

/**
 * Created by Tomal on 7/28/2016.
 */
public class SubmitChectoutAsyntask extends AsyncTask<String,String,String> {
    private Context context;
    public SubmitChectoutAsyntask(Context context){
        this.context=context;
    }


    @Override
    protected String doInBackground(String... params) {
        String firstName=params[0];
        String lastName=params[1];
        String email=params[2];
        String phone=params[3];
        String address=params[4];
        String city=params[5];
        String orderFrom=params[6];
        String shipping_address=params[7];
        String shipping_country=params[8];
        String shipping_zipcode=params[9];
        String shipping_city=params[10];
        String delivery_method_id=params[11];
        String payment_method_id=params[12];
        String currency_id=params[13];
        String shopping_cart=params[14];

        return new SubmitCheckoutService().submitCheckout(firstName,lastName,email,phone,address,city,orderFrom,shipping_address,
                shipping_country,shipping_zipcode,shipping_city,delivery_method_id,payment_method_id,currency_id,shopping_cart);

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

}
