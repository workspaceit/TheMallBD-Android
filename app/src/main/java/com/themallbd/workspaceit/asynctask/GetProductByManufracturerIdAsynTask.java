package com.themallbd.workspaceit.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.ProductByManufracturerActivity;
import com.themallbd.workspaceit.service.ManufracturerService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 9/9/2016.
 */
public class GetProductByManufracturerIdAsynTask extends AsyncTask<String,String,Boolean> {
    private Activity activity;
    private ProgressDialog mProgressDialog;

    public GetProductByManufracturerIdAsynTask(Activity activity){
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog=new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading produts.. please wait...");
        mProgressDialog.show();
    }



    @Override
    protected Boolean doInBackground(String... params) {
        String manufracturerId=params[0];
        return new ManufracturerService().getProductByManufracturerId(manufracturerId);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (aBoolean){
            if (activity instanceof ProductByManufracturerActivity){
                ((ProductByManufracturerActivity)activity).MakeAdapterWork();
            }
        }else {
            MakeToast.showToast(activity, Utility.responseStat.msg);
        }
    }
}
