package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.ProductDetailsActivity;
import com.themallbd.workspaceit.dataModel.Review;
import com.themallbd.workspaceit.service.ReviewService;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.activity.ShowAllReviewActivity;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/26/2016.
 */
public class GetReviewAsynTask extends AsyncTask<String, String,ArrayList<Review>> {

    private Context context;
    private int flag;
    private ProgressDialog mProgressDialog;

    public GetReviewAsynTask(Context context,int flag){
        this.context=context;
        this.flag=flag;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(flag!=1){
            mProgressDialog = new ProgressDialog(this.context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Loading Related Products...");
            mProgressDialog.show();
        }
    }

    @Override
    protected ArrayList<Review> doInBackground(String... params) {

        String productId=params[0];
        String limit=params[1];
        String offset=params[2];



        return new ReviewService().getReviewList(productId,limit,offset);

    }


    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        super.onPostExecute(reviews);
        if(flag!=1){
            mProgressDialog.dismiss();
        }

        Utility.reviews.clear();
        if(reviews.size()>0){
            Utility.reviews.addAll(reviews);

            if(flag==1) {
                ((ProductDetailsActivity) context).setReviewDatasetAdapter();
            }else {
                ((ShowAllReviewActivity)context).setReviewDataSet();
            }
        }
    }
}
