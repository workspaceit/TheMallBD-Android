package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.service.GetBannerImageService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 7/21/2016.
 */
public class GetBannerImagesAsyncTask extends AsyncTask<String, String, Boolean> {
    private MainActivity mainActivity;


    public GetBannerImagesAsyncTask(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        return new GetBannerImageService().getAllBannerImage();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            mainActivity.initializeSlider(true);

        } else {
            MakeToast.showToast(mainActivity, Utility.responseStat.msg);

        }
    }
}
