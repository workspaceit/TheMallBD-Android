package com.themallbd.workspaceit.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.AllDiscountProductActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.ProductService;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/25/2016.
 */
public class GetSpecailDiscountProductAsynTask extends AsyncTask<String, String, ArrayList<Products>> {

    private Context context;

    public GetSpecailDiscountProductAsynTask(Context context) {
        this.context=context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected ArrayList<Products> doInBackground(String... params) {
        String limit = params[0];
        String offset = params[1];


        return new ProductService().getDiscountProduct(limit,offset);
    }


    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        if(Utility.responseStat.status){

            if(context instanceof MainActivity){
                if (MainActivity.discountProductForHorizontalList.size()!=0) {
                    MainActivity.discountProductForHorizontalList.remove(MainActivity.discountProductForHorizontalList.size() - 1);
                }
                MainActivity.discountProductForHorizontalList.addAll(productses);

                ((MainActivity) context).notifyDiscountProductDataSet();

            }else if(context instanceof AllDiscountProductActivity){
                MainActivity.discountProductForHorizontalList.addAll(productses);
                ((AllDiscountProductActivity)context).notifyDataSetChange();
            }


        }else {

            if(context instanceof MainActivity){
                if (MainActivity.discountProductForHorizontalList.size()!=0) {
                    MainActivity.discountProductForHorizontalList.remove(MainActivity.discountProductForHorizontalList.size() - 1);
                    ((MainActivity) context).setDiscountProductError();
                }

            }else if (context instanceof AllDiscountProductActivity){
                ((AllDiscountProductActivity) context).newProductError();
            }

        }
    }
}
