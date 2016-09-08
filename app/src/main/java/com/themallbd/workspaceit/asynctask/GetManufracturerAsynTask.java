package com.themallbd.workspaceit.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.ManufracturerActivity;
import com.themallbd.workspaceit.service.ManufracturerService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 9/8/2016.
 */
public class GetManufracturerAsynTask extends AsyncTask<String,String,Boolean> {
    private Activity activity;
    private ProgressDialog mProgressDialog;

    public GetManufracturerAsynTask(Activity activity){
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog=new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading Manufacturers. Please Wait..");
        mProgressDialog.show();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        return new ManufracturerService().getAllManufracturer();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (aBoolean){
            if (activity instanceof ManufracturerActivity){
                ((ManufracturerActivity)activity).notifyDataSetForManufracturer();
            }
        }else {
            MakeToast.showToast(activity, Utility.responseStat.msg);
        }
    }

}
