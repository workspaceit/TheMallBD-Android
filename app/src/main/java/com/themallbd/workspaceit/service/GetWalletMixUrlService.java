package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.Banner;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

import java.util.Collections;

/**
 * Created by Tomal on 8/8/2016.
 */
public class GetWalletMixUrlService extends BaseMallBDService {
    private ResponseStat responseStat;

    public String getWalletMixConfrimation(String orderId){
        this.responseStat = new ResponseStat();
        String url="";
        this.setController("api/walletmix");
        this.setParams("order_id",orderId);

        String resp = this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {
                 url = gson.fromJson(jsonObject.get("wmx_url"),String.class);
                Utility.responseStat=responseStat;
                return url;

            } else {
                Utility.responseStat = this.responseStat;
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return url;
    }
}
