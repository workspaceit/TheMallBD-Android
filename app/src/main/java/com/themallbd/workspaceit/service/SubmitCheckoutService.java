package com.themallbd.workspaceit.service;

import com.themallbd.workspaceit.dataModel.ResponseStat;

/**
 * Created by Tomal on 7/28/2016.
 */
public class SubmitCheckoutService extends BaseMallBDService{
    private ResponseStat responseStat;

    public String submitCheckout(String first_name,String last_name,String email,String phone, String address,String city,String order_from ,
                                  String shipping_address,String shipping_country, String shipping_zipcode,String shipping_city,
                                  String delivery_method_id,String payment_method_id,String currency_id,String shopping_cart){

        this.responseStat=new ResponseStat();
        this.setController("api/checkout/submit");
        this.setParams("first_name", first_name);
        this.setParams("last_name",last_name);
        this.setParams("email",email);
        this.setParams("phone",phone);
        this.setParams("address",address);
        this.setParams("city",city);
        this.setParams("order_from",order_from);
        this.setParams("shipping_address",shipping_address);
        this.setParams("shipping_country",shipping_country);
        this.setParams("shipping_zipcode",shipping_zipcode);
        this.setParams("shipping_city",shipping_city);
        this.setParams("delivery_method_id",delivery_method_id);
        this.setParams("payment_method_id",payment_method_id);
        this.setParams("currency_id",currency_id);
        this.setParams("shopping_cart", shopping_cart);



        String resp=this.getData("POST");
        System.out.println(resp);

        return resp;

    }
}
