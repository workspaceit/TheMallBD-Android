package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.Banner;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.dataModel.SearchResult;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Tomal on 7/21/2016.
 */
public class GetBannerImageService extends BaseMallBDService {
    private ResponseStat responseStat;

    public boolean getAllBannerImage(){
        this.responseStat = new ResponseStat();
        this.setController("api/banner/all");


        String resp = this.getData("GET");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {
                Banner[] banners = gson.fromJson(jsonObject.get("responseData"), Banner[].class);


                Collections.addAll(Utility.banners, banners);


                return true;
            } else {
                Utility.responseStat = this.responseStat;
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }
}
