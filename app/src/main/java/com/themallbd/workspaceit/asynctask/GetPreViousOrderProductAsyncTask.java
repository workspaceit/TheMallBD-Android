package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.PrevoiusOrderActivity;
import com.themallbd.workspaceit.service.GetPreviousOrderService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 8/4/2016.
 */
public class GetPreViousOrderProductAsyncTask extends AsyncTask<String,String,Boolean> {
    private Context context;
    private ProgressDialog mProgressDialog;

    public GetPreViousOrderProductAsyncTask(Context context){
        this.context=context;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Getting your Order History. Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return new GetPreviousOrderService().getPreviousOrder();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (aBoolean){
                if (context instanceof PrevoiusOrderActivity){
                    ((PrevoiusOrderActivity)context).notifyDataSet();
                }

        }else {
            MakeToast.showToast(context, Utility.responseStat.msg);
        }
    }


}
