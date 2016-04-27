package com.workspaceit.themallbd.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.workspaceit.themallbd.service.ReviewService;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

/**
 * Created by Mausum on 4/27/2016.
 */
public class AddNewReviewAsynTask extends AsyncTask<String, String, Boolean> {

    private Context context;
    private ProgressDialog mProgressDialog;


    public AddNewReviewAsynTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Adding New Review...");
        mProgressDialog.show();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        String productId = params[0];
        String note = params[1];
        String rating = params[2];

        return new ReviewService().addNewReviewService(productId, rating, note);


    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (aBoolean) {
            MakeToast.showToast(context, "Review Added Succesfully");
        } else {
            if (Utility.responseStat.msg != null) {
                MakeToast.showToast(context, Utility.responseStat.msg);
            } else {
                MakeToast.showToast(context, "Check your Interner Connection");
            }
        }
    }
}
