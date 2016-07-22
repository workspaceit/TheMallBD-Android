package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.themallbd.workspaceit.service.AuthenticationService;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 7/22/2016.
 */
public class LoginByAccesTokenAsynTask extends AsyncTask<String,String,Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        String accessToken=params[0];

        return new AuthenticationService().loginWithAccessToken(accessToken);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            Utility.isLoggedInFlag=true;

        }else {
            Utility.isLoggedInFlag=false;

        }
    }
}
