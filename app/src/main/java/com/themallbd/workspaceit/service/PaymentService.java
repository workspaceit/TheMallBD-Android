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
public class PaymentService extends BaseMallBDService {
    private ResponseStat responseStat;

    public boolean acceptOrder(String orderId){
        this.responseStat=new ResponseStat();
        this.setController("api/paymentsuccess");
        this.setParams("order_id", orderId);

        String resp = this.getData("POST");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {


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

public boolean cancelOrder(String orderId){
    this.responseStat=new ResponseStat();
    this.setController("api/paymentcancel");
    this.setParams("order_id", orderId);

    String resp = this.getData("POST");
    System.out.println(resp);
    try {
        JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
        Gson gson = new Gson();

        this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

        if (this.responseStat.status) {


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

    public boolean confrimWalletMixPayment(String orderId){
        this.responseStat=new ResponseStat();
        this.setController("api/paymentstatuscheckbyid");
        this.setParams("order_id", orderId);

        String resp = this.getData("POST");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {


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

    public boolean confirmBkashpayment(String uniqueCode,String mobileNumber, String transactionId){
        this.responseStat=new ResponseStat();
        this.setController("api/bkash-data-process");
        this.setParams("unique_code", uniqueCode);
        this.setParams("mobileNumber",mobileNumber);
        this.setParams("transactionId",transactionId);
        String resp = this.getData("POST");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {


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
