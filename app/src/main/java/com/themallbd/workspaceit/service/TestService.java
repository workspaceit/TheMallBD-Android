package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.ResponseStat;

/**
 * Created by rajib on 2/11/16.
 */
public class TestService extends BaseMallBDService {

    private ResponseStat responseStat;

    public Boolean testConnection()
    {
        this.setController("api/category/childs/show");
        this.setParams("shop_id", "1");
        this.setParams("parent_id","4");
        String response = this.getData("POST");

        this.responseStat = new ResponseStat();

        System.out.println(response);
        try {
            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            System.out.println("Status:"+this.responseStat.status+",isLogin: "+this.responseStat.isLogin);

            return this.responseStat.status;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
