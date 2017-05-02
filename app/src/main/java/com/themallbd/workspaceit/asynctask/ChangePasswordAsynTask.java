package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.themallbd.workspaceit.activity.LoginActivity;
import com.themallbd.workspaceit.activity.MyAccountActivity;
import com.themallbd.workspaceit.service.AuthenticationService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 8/11/2016.
 */
public class ChangePasswordAsynTask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private ProgressDialog mProgressDialog;
    private SessionManager sessionManager;

    public ChangePasswordAsynTask(Context context) {
        this.context = context;
        this.sessionManager = new SessionManager(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Updating your account information. Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        String oldPassword = params[0];
        String newPassword = params[1];


        return new AuthenticationService().changePassword(oldPassword, newPassword);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (aBoolean) {
            MakeToast.showToast(context, "Password changed successfully.. Now Login is necessary");
            sessionManager.logoutUser();
            Utility.isLoggedInFlag = false;
            Utility.loggedInUser = null;
            if (context instanceof MyAccountActivity) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((MyAccountActivity) context).finish();

            }
        } else {

            MakeToast.showToast(context, Utility.responseStat.msg);
        }


    }
}
