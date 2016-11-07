package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.activity.PackageDetailsActivity;
import com.themallbd.workspaceit.dataModel.Banner;
import com.themallbd.workspaceit.dataModel.CustomerPurchaseDiscount;
import com.themallbd.workspaceit.dataModel.DeliveryMethod;
import com.themallbd.workspaceit.dataModel.PaymentMethods;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.fragment.CheckoutViewFragment;
import com.themallbd.workspaceit.utility.Utility;

import java.util.Collections;

/**
 * Created by Tomal on 8/1/2016.
 */
public class GetPaymentAndDeliveyMethodsService extends BaseMallBDService{
    private ResponseStat responseStat;

    public boolean getPaymentMethods(){
        this.responseStat = new ResponseStat();
        this.setController("api/paymentmethods/all");


        String resp = this.getData("GET");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {
                PaymentMethods[] paymentMethodses = gson.fromJson(jsonObject.get("responseData"), PaymentMethods[].class);


                Collections.addAll(Utility.paymentMethodses, paymentMethodses);


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


    public boolean getDeliveryMrthod(){
        this.responseStat = new ResponseStat();
        this.setController("api/deliverymethod/all");


        String resp = this.getData("GET");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {
                DeliveryMethod[] deliveryMethods = gson.fromJson(jsonObject.get("responseData"), DeliveryMethod[].class);


                Collections.addAll(Utility.deliveryMethods, deliveryMethods);


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


    public boolean getPurchaseDiscount(){
        this.responseStat = new ResponseStat();
        this.setController("api/getcustomerpurchasediscount");


        String resp = this.getData("GET");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {
                CustomerPurchaseDiscount[]customerPurchaseDiscounts= gson.fromJson(jsonObject.get("responseData"), CustomerPurchaseDiscount[].class);
                CheckoutViewFragment.dicount_message=gson.fromJson(jsonObject.get("discountMessage"),String.class);
                Utility.customerPurchaseDiscount=customerPurchaseDiscounts[0];


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
