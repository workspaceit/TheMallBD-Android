package com.themallbd.workspaceit.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.themallbd.workspaceit.service.RegistrationService;
import com.themallbd.workspaceit.activity.LoginActivity;
import com.themallbd.workspaceit.fragment.RegistrationFragment;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Mausum on 4/7/2016.
 */
public class RegistrationAsynTask extends AsyncTask<String,String,Boolean> {
    private Activity context;
    private ProgressDialog mProgressDialog;


    public RegistrationAsynTask(Activity activity){
        this.context=activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Completing Registration...");
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String fname=params[0];
        String lname=params[1];
        String email=params[2];
        String phone=params[3];
        String password=params[4];
        String confirmPassword=params[5];

        RegistrationService registrationService=new RegistrationService();


        return registrationService.completeRegistration(fname,lname,email,phone,password,confirmPassword);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if(aBoolean){
            MakeToast.showToast(context,"Registration Succesfull");
            RegistrationFragment.fnameText.setText("");
            RegistrationFragment.lnameText.setText("");
            RegistrationFragment.emailText.setText("");
            RegistrationFragment.phoneText.setText("");
            RegistrationFragment.passwordText.setText("");
            RegistrationFragment.confrimPassText.setText("");
            LoginActivity.mViewPager.setCurrentItem(0);



        }else {
            if(Utility.responseStat.msg!=null){
                MakeToast.showToast(context,Utility.responseStat.msg);
            }else {
                MakeToast.showToast(context,"Registration Failed");
            }
        }
    }
}
