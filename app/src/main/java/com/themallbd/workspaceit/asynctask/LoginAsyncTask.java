package com.themallbd.workspaceit.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.service.AuthenticationService;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by rajib on 2/19/16.
 */
public class LoginAsyncTask extends AsyncTask<String,String,Boolean> {

    private Activity context;
    private ProgressDialog mProgressDialog;
    private SessionManager mSessionManager;

    public LoginAsyncTask(Activity activity) {
        this.context = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Logging in...");
        mProgressDialog.show();

    }

    @Override
    protected Boolean doInBackground(String... params) {
        String email = params[0];
        String password = params[1];
        AuthenticationService authenticationService = new AuthenticationService();
        return authenticationService.loginWithPassword(email,password);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (aBoolean)
        {
            Toast.makeText(this.context,"Login Successful",Toast.LENGTH_SHORT).show();
            mSessionManager = new SessionManager(this.context);
            Utility.isLoggedInFlag=true;
            mSessionManager.createLoginSession(
                    Utility.loggedInUser.accesstoken,
                    Utility.loggedInUser.email,Utility.loggedInUser.user.id,
                    Utility.loggedInUser.user.firstName +" "+Utility.loggedInUser.user.lastName,Utility.loggedInUser.user.firstName,
                    Utility.loggedInUser.user.lastName,
                    Utility.loggedInUser.user.userDetails.address.address,Utility.loggedInUser.user.userDetails.address.zipCode,
                    Utility.loggedInUser.user.phone,Utility.loggedInUser.user.userDetails.address.city,
                    Utility.loggedInUser.user.userDetails.address.country);

            this.context.finish();
        }
        else {
            if (Utility.responseStat.msg!=null)
            {
                Toast.makeText(this.context,Utility.responseStat.msg,Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(this.context,"Login Failed!",Toast.LENGTH_SHORT).show();
        }
    }
}
