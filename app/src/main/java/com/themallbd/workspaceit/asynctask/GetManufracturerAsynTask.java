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


    public GetManufracturerAsynTask(Activity activity){
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        String limit=params[0];
        String offset=params[1];
        return new ManufracturerService().getAllManufracturer(limit,offset);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (aBoolean){
            if (activity instanceof ManufracturerActivity){
                ((ManufracturerActivity)activity).notifyDataSetForManufracturer();
            }
        }else {
            if (activity instanceof ManufracturerActivity){
                ((ManufracturerActivity)activity).manufracturerError();
            }
        }
    }

}
