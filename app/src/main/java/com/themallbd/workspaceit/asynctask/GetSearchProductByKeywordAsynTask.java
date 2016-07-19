package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.SearchProductListActivity;
import com.themallbd.workspaceit.service.GetSearchProductService;

/**
 * Created by Tomal on 7/19/2016.
 */
public class GetSearchProductByKeywordAsynTask extends AsyncTask<String,String,Boolean> {

    private SearchProductListActivity activity;
    private ProgressDialog mProgressDialog;

    public GetSearchProductByKeywordAsynTask(SearchProductListActivity searchProductListActivity){
        activity=searchProductListActivity;
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Searching Products...");
        mProgressDialog.show();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected Boolean doInBackground(String... params) {
        String keyword=params[0];
        String limit=params[1];
        String offset=params[2];



        return new GetSearchProductService().getProductArrayByKeyword(keyword,limit,offset);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();

            activity.notifyDataSetChange();


    }
}
