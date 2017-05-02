package com.themallbd.workspaceit.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.dataModel.AppCredential;
import com.themallbd.workspaceit.dataModel.ResponseStat;

/**
 * Created by rajib on 2/19/16.
 */

public class AuthenticationService extends BaseMallBDService {
    private ResponseStat responseStat;
    private AppCredential appCredential;


    public boolean changePassword(String oldPassword,String newPassword){
        this.responseStat=new ResponseStat();
        this.setController("api/pass/reset");
        this.setParams("o_password", oldPassword);
        this.setParams("n_password", newPassword);

        String resp = this.getData("POST");




        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status && this.responseStat.isLogin)
            {
               Utility.responseStat=this.responseStat;
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


    public boolean loginWithPassword(String email,String password)
    {
        this.responseStat = new ResponseStat();
        this.appCredential = new AppCredential();
        this.setController("login");
        this.setParams("email", email);
        this.setParams("password", password);

        String resp = this.getData("POST");



        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status && this.responseStat.isLogin)
            {
                appCredential = gson.fromJson(jsonObject.get("responseData"),AppCredential.class);
                Utility.loggedInUser = appCredential;
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

    public boolean loginWithAccessToken(String accesstoken) {
        this.responseStat = new ResponseStat();
        this.appCredential = new AppCredential();
        this.setController("api/login/accesstoken");
        this.setParams("accesstoken", accesstoken);


        String resp = this.getData("POST");




        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status && this.responseStat.isLogin)
            {
                appCredential = gson.fromJson(jsonObject.get("responseData"),AppCredential.class);
                Utility.loggedInUser = appCredential;
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
}
