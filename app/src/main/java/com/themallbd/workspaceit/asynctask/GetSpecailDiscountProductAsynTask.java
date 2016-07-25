package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.service.ProductService;

/**
 * Created by Tomal on 7/25/2016.
 */
public class GetSpecailDiscountProductAsynTask extends AsyncTask<String, String, Boolean> {

    MainActivity mainActivity;

    public GetSpecailDiscountProductAsynTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        String limit = params[0];
        String offset = params[1];


        return new ProductService().getDiscountProduct(limit,offset);
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            mainActivity.notifyDiscountProductDataSet();
        }else {
            mainActivity.setDiscountProductError();
        }
    }
}
