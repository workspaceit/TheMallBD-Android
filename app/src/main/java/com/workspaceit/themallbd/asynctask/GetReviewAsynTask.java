package com.workspaceit.themallbd.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;

import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.activity.ProductDetailsActivity;
import com.workspaceit.themallbd.activity.ShowAllReviewActivity;
import com.workspaceit.themallbd.dataModel.ProductAttributeCombination;
import com.workspaceit.themallbd.dataModel.Review;
import com.workspaceit.themallbd.service.ReviewService;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

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
