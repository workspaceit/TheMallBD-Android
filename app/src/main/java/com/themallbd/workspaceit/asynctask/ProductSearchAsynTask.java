package com.themallbd.workspaceit.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.BaseActivityWithoutDrawer;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.service.GetSearchProductService;
import com.themallbd.workspaceit.utility.AutoCompleteTextChangeLisnter;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.utility.MakeToast;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/19/2016.
 */
public class ProductSearchAsynTask extends AsyncTask<String,String,ArrayList<String>> {
    private String shopID="1";
    Context contex;
    private short decidingFlag;


    public ProductSearchAsynTask(Context context,short flag){
        this.contex=context;
        this.decidingFlag=flag;

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

        if(this.decidingFlag==1)
            AutoCompleteTextChangeLisnter.callFlag=true;
        else if(this.decidingFlag==2)
            BaseActivityWithoutDrawer.otherPageSearchCallFlag=true;

        if(productTitles.size()>0){

            if(this.decidingFlag==1){
                ((MainActivity)contex).setSeacrhAdater();

            }else if (decidingFlag==2){
                ((BaseActivityWithoutDrawer)contex).notifyOnSeacrDataChnaged();

            }
        }else {
            MakeToast.showToast(contex, "Nothing Found..");
        }






    }

}
