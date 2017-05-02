package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.activity.PrevoiusOrderActivity;
import com.themallbd.workspaceit.dataModel.Orders;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

import java.util.Collections;

/**
 * Created by Tomal on 8/4/2016.
 */
public class GetPreviousOrderService extends BaseMallBDService {
    private ResponseStat responseStat;


    public boolean getPreviousOrder(){
        this.responseStat=new ResponseStat();
        this.setController("api/order/getbyuser/mobile");

        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){
                Orders[] orderses = gson.fromJson(jsonObject.get("responseData"),Orders[].class);
                Collections.addAll(PrevoiusOrderActivity.ordersArrayList,orderses);
                Utility.responseStat=this.responseStat;
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
