package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Tomal on 7/25/2016.
 */
public class PackageService extends BaseMallBDService {

    private ResponseStat responseStat;
    private ArrayList<MallBdPackage> mallBdPackages;

    public ArrayList<MallBdPackage> getAllPackages(String limit, String offset){
        this.responseStat = new ResponseStat();
        this.mallBdPackages=new ArrayList<>();

        this.setController("api/package/all/mobile");
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


                Utility.responseStat=this.responseStat;
                MallBdPackage[] packages = gson.fromJson(jsonObject.get("responseData"),MallBdPackage[].class);
                Collections.addAll(this.mallBdPackages,packages);
                return this.mallBdPackages;
            }
            else {
                Utility.responseStat = this.responseStat;
                return this.mallBdPackages;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return this.mallBdPackages;
    }
}
