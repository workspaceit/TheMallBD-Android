package com.workspaceit.themallbd.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.workspaceit.themallbd.activity.ProductDetailsActivity;
import com.workspaceit.themallbd.service.ReviewService;
import com.workspaceit.themallbd.utility.MakeToast;

/**
 * Created by Mausum on 4/26/2016.
 */
public class GetReviewCountAsynTask extends AsyncTask<String,String,Integer> {
    private Context context;

    public GetReviewCountAsynTask(Activity activity){
        this.context=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        return new ReviewService().getReviewCount(params[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        ((ProductDetailsActivity)context).reviewCountSet(integer);
    }
}
