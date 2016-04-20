package com.workspaceit.themallbd.asynctask;

import android.os.AsyncTask;

import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.adapter.SearchProductAdapter;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.SearchResult;
import com.workspaceit.themallbd.service.GetSearchProductService;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/19/2016.
 */
public class ProductSearchAsynTask extends AsyncTask<String,String,ArrayList<String>> {
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
    protected ArrayList<String> doInBackground(String... params) {
        String keyword=params[0];





        GetSearchProductService getSearchProductService=new GetSearchProductService();
        return getSearchProductService.getSearcProduct(keyword);
    }





    @Override
    protected void onPostExecute(ArrayList<String> productTitles) {
        super.onPostExecute(productTitles);

        if(productTitles.size()>0){

            contex.setSeacrhAdater();
            MakeToast.showToast(contex,String.valueOf(Utility.searchProductTitle.size()));


        }else {
            MakeToast.showToast(contex, Utility.responseStat.msg);
        }
    }

}
