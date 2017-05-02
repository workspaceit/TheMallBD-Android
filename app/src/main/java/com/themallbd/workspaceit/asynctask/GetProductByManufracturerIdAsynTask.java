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


    public GetProductByManufracturerIdAsynTask(Activity activity){
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }



    @Override
    protected Boolean doInBackground(String... params) {
        String manufracturerId=params[0];
        String limit=params[1];
        String offset=params[2];
        return new ManufracturerService().getProductByManufracturerId(manufracturerId,limit,offset);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (aBoolean){
            if (activity instanceof ProductByManufracturerActivity){
                ((ProductByManufracturerActivity)activity).MakeAdapterWork();
            }
        }else {
            if (activity instanceof ProductByManufracturerActivity){
                ((ProductByManufracturerActivity)activity).setError();
            }
        }
    }
}
