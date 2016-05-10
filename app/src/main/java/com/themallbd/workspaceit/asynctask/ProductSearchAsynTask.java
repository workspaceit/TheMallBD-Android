package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.service.GetSearchProductService;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.utility.MakeToast;

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
