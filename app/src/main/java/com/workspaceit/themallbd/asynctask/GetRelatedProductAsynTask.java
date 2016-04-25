package com.workspaceit.themallbd.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.workspaceit.themallbd.activity.ProductDetailsActivity;
import com.workspaceit.themallbd.activity.ShowRelatedProduct;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.GetSearchProductService;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/25/2016.
 */
public class GetRelatedProductAsynTask extends AsyncTask<String,String,ArrayList<Products>> {

    private Context context;
    private int flag;
    private ProgressDialog mProgressDialog;

    public GetRelatedProductAsynTask(Activity activity,int flag){
        context=activity;
        this.flag=flag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(flag!=1){
            mProgressDialog = new ProgressDialog(this.context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Loading Related Products...");
            mProgressDialog.show();
        }
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {
        String limt=params[0];
        String offset=params[1];
        String productId=params[2];
        String categoryId=params[3];

        return new GetSearchProductService().getRelatedProduct(limt,offset,productId,categoryId);
    }



    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        if(flag!=1){
            mProgressDialog.dismiss();
        }

        Utility.relatedProductArryList.clear();
        if(productses.size()>0){
            Utility.relatedProductArryList.addAll(productses);
            if(flag==1) {

                ((ProductDetailsActivity) context).setDataSetAdapter();
            }else {
                ((ShowRelatedProduct)context).setAdapterDataSet();
            }

        }else {

        }
    }

}
