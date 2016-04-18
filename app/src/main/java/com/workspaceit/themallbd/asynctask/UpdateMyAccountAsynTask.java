package com.workspaceit.themallbd.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.workspaceit.themallbd.activity.MyAccountActivity;
import com.workspaceit.themallbd.fragment.RegistrationFragment;
import com.workspaceit.themallbd.service.RegistrationService;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.SessionManager;
import com.workspaceit.themallbd.utility.Utility;

/**
 * Created by Mausum on 4/18/2016.
 */
public class UpdateMyAccountAsynTask extends AsyncTask<String,String,Boolean> {

    private MyAccountActivity context;
    private ProgressDialog mProgressDialog;

    public UpdateMyAccountAsynTask(MyAccountActivity activity){
        this.context=activity;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Updating Your Account Information...");
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String fname=params[0];
        String lname=params[1];
        String phone=params[2];
        String address=params[3];
        String country=params[4];
        String city=params[5];
        String zip=params[6];

        RegistrationService registrationService=new RegistrationService();

        return registrationService.upadetUserInformation(fname,lname,phone,address,country,city,zip);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        mProgressDialog.dismiss();
        if(aBoolean){
            MakeToast.showToast(context, "Information Updated Successfully");
            context.restFab();
        }else {
            if(Utility.responseStat.msg!=null){
                MakeToast.showToast(context, Utility.responseStat.msg);
            }else {
                MakeToast.showToast(context, "Updation failed");
            }
        }
    }
}
