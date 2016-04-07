package com.workspaceit.themallbd.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workspaceit.themallbd.dataModel.AppCredential;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.utility.Utility;

/**
 * Created by Mausum on 4/7/2016.
 */
public class RegistrationService extends BaseMallBDService {
    private ResponseStat responseStat;
    private AppCredential appCredential;

    public boolean completeRegistration(String fname,String lname,String email, String phone, String password){
        this.responseStat=new ResponseStat();
        this.appCredential=new AppCredential();
        this.setController("api/registration/register");
        this.setParams("first_name", fname);
        this.setParams("last_name",lname);
        this.setParams("email",email);
        this.setParams("phone",phone);
        this.setParams("password", password);
        String resp=this.getData("POST");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){

                return true;
            }else {
                Utility.responseStat = this.responseStat;
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();

        }

        return false;
    }
}
