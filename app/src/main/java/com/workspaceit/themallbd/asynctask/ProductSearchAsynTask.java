package com.workspaceit.themallbd.asynctask;

import android.os.AsyncTask;

import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.adapter.SearchProductAdapter;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.service.GetSearchProductService;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/19/2016.
 */
public class ProductSearchAsynTask extends AsyncTask<String,String,ArrayList<Products>> {
    private String shopID="1";
    MainActivity contex;


    public ProductSearchAsynTask(MainActivity activity){
        this.contex=activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {
        String keyword=params[0];
        String limit=params[1];
        String offset=params[2];




        GetSearchProductService getSearchProductService=new GetSearchProductService();
        return getSearchProductService.getSearcProduct(keyword,shopID,limit,offset);
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);

        if(productses.size()>0){
            contex.setSeacrhAdater();


        }else {
            MakeToast.showToast(contex, Utility.responseStat.msg);
        }
    }

}
