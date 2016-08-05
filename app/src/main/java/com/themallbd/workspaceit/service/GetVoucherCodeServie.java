package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.dataModel.SearchResult;
import com.themallbd.workspaceit.dataModel.Voucher;
import com.themallbd.workspaceit.utility.Utility;

import java.util.Collections;

/**
 * Created by Tomal on 8/5/2016.
 */
public class GetVoucherCodeServie extends BaseMallBDService {
    private ResponseStat responseStat;

    public Voucher getVoucherCode(String voucherCode){
        this.responseStat = new ResponseStat();
        Voucher voucher=null;

        this.setController("api/checkout/voucher");
        this.setParams("voucher_code", voucherCode);

        String resp = this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

                voucher = gson.fromJson(jsonObject.get("responseData"), Voucher.class);





                return voucher;
            } else {
                Utility.responseStat = this.responseStat;
                return voucher;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return voucher;


    }
}
