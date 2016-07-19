package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.AppCredential;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Mausum on 4/7/2016.
 */
public class RegistrationService extends BaseMallBDService {
    private ResponseStat responseStat;
    private AppCredential appCredential;

    public boolean completeRegistration(String fname, String lname, String email, String phone, String password, String confirmPassword) {
        this.responseStat = new ResponseStat();
        this.appCredential = new AppCredential();
        this.setController("api/registration/register");
        this.setParams("first_name", fname);
        this.setParams("last_name", lname);
        this.setParams("email", email);
        this.setParams("phone", phone);
        this.setParams("password", password);
        this.setParams("confirm_password",confirmPassword);
        String resp = this.getData("POST");

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

                return true;
            } else {
                Utility.responseStat = this.responseStat;
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

    public boolean upadetUserInformation(String fname, String lname, String phone, String addrs, String country, String city, String zip) {

        this.responseStat = new ResponseStat();
        this.appCredential = new AppCredential();
        this.setController("api/profile/update");
        this.setParams("first_name", fname);
        this.setParams("last_name", lname);
        this.setParams("phone", phone);
        this.setParams("address", addrs);
        this.setParams("zip_code", zip);
        this.setParams("city", city);
        this.setParams("country", country);

        String resp = this.getData("POST");

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status && this.responseStat.isLogin) {

                appCredential = gson.fromJson(jsonObject.get("responseData"), AppCredential.class);
                Utility.loggedInUser = appCredential;

                return true;
            } else {
                Utility.responseStat = this.responseStat;
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;


    }


}
