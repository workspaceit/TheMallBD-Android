package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

import java.util.Collections;

/**
 * Created by Tomal on 7/25/2016.
 */
public class PackageService extends BaseMallBDService {

    private ResponseStat responseStat;

    public boolean getAllPackages(String limit, String offset){
        this.responseStat = new ResponseStat();


        this.setController("api/package/all");
        this.setParams("offset", offset);
        this.setParams("limit",limit);


        String resp = this.getData("POST");
        System.out.println(resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status)
            {



                MallBdPackage[] packages = gson.fromJson(jsonObject.get("responseData"),MallBdPackage[].class);
                Collections.addAll(MainActivity.packgeProductForHorizontalList,packages);
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
        return false;
    }
}
