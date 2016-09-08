package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.Manufacturer;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.dataModel.Voucher;
import com.themallbd.workspaceit.utility.Utility;

import java.util.Collections;

/**
 * Created by Tomal on 9/8/2016.
 */
public class ManufracturerService extends BaseMallBDService {
    private ResponseStat responseStat;

    public boolean getAllManufracturer(){

        this.responseStat = new ResponseStat();
        this.setController("api/manufacturer/all");


        String resp = this.getData("GET");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

                Manufacturer[]manufacturers = gson.fromJson(jsonObject.get("responseData"), Manufacturer[].class);
                Collections.addAll(Utility.manufacturers,manufacturers);

                Utility.responseStat=responseStat;
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
