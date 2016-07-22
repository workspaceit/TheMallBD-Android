package com.themallbd.workspaceit.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mausum on 4/11/2016.
 */
public class WishListService extends BaseMallBDService {
    private ResponseStat responseStat;

    public boolean addProductToWisgList(String customerId,String productId){
        this.responseStat=new ResponseStat();
        this.setController("api/customer/wishlist/add");
        this.setParams("customer_id", customerId);
        this.setParams("product_id",productId);
        String resp=this.getData("POST");

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){

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

    public ArrayList<Products> getProductOfWishList(){
        ArrayList<Products> wishListProduct=new ArrayList<>();
        this.responseStat=new ResponseStat();
        this.setController("api/customer/wishlist/all");

        String resp=this.getData("GET");
        System.out.print(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();


            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){


                    Products[] products = gson.fromJson(jsonObject.get("responseData"),Products[].class);
                    Collections.addAll(wishListProduct,products);
                    Collections.addAll(Utility.wishlistProductArrayList,products);
                    return wishListProduct;



            }else {
                Utility.responseStat = this.responseStat;


                return wishListProduct;
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return wishListProduct;

    }
}
