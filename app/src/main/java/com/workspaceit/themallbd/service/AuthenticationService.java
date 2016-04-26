package com.workspaceit.themallbd.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workspaceit.themallbd.dataModel.AppCredential;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.utility.Utility;

import java.util.Collections;

/**
 * Created by rajib on 2/19/16.
 */

public class AuthenticationService extends BaseMallBDService {
    private ResponseStat responseStat;
    private AppCredential appCredential;

    public boolean loginWithPassword(String email,String password)
    {
        this.responseStat = new ResponseStat();
        this.appCredential = new AppCredential();
        this.setController("login");
        this.setParams("email", email);
        this.setParams("password",password);

        String resp = this.getData("POST");


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status && this.responseStat.isLogin)
            {
                appCredential = gson.fromJson(jsonObject.get("responseData"),AppCredential.class);
                Utility.loggedInUser = appCredential;
                System.out.println("isLogin: " + this.responseStat.isLogin);
                Log.i("Check", "true");
                return true;

            }
            else {
                Utility.responseStat = this.responseStat;
                return false;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return  false;
    }

    public boolean loginWithAccessToken(String accesstoken)
    {
        this.responseStat = new ResponseStat();
        this.appCredential = new AppCredential();
        return  true;
    }
}
