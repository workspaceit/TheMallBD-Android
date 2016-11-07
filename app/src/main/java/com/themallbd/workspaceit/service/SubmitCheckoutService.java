package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.FinishOrderSummary;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.fragment.PaymentFragment;
import com.themallbd.workspaceit.utility.Utility;

/**
 * Created by Tomal on 7/28/2016.
 */
public class SubmitCheckoutService extends BaseMallBDService{
    private ResponseStat responseStat;

    public boolean submitCheckout(String firstName,String lastName,String email,String phone,String address,
                                  String city,String orderFrom, String invoice_address,String shipping_firstname,String shipping_lastname,
                                  String shipping_phone,String shipping_address,String shipping_city,String delivery_method_id,
                                  String payment_method_id,String currency_id,String voucherDiscountDetails,String customerDiscount,
                                  String customerDiscountDetails,String Shopping_cart){

        this.responseStat=new ResponseStat();
        this.setController("api/checkout/submitWeb");
        this.setParams("first_name", firstName);
        this.setParams("last_name",lastName);
        this.setParams("email",email);
        this.setParams("phone",phone);
        this.setParams("address",address);
        this.setParams("city",city);


        this.setParams("order_from",orderFrom);
        this.setParams("invoice_address",invoice_address);
        this.setParams("shipping_firstname",shipping_firstname);
        this.setParams("shipping_lastname",shipping_lastname);
        this.setParams("shipping_phone",shipping_phone);
        this.setParams("shipping_address",shipping_address);
        this.setParams("shipping_city",shipping_city);

        this.setParams("delivery_method_id",delivery_method_id);
        this.setParams("payment_method_id",payment_method_id);
        this.setParams("currency_id",currency_id);

        this.setParams("voucherDiscountDetails",voucherDiscountDetails);
        this.setParams("customerDiscount",customerDiscount);
        this.setParams("customerDiscountDetails",customerDiscountDetails);

        this.setParams("shopping_cart", Shopping_cart);



        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){
                FinishOrderSummary finishOrderSummary = gson.fromJson(jsonObject.get("responseData"),FinishOrderSummary.class);
                Utility.finishOrderSummary=finishOrderSummary;
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
