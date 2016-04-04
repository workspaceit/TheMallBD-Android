package com.workspaceit.themallbd.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rajib on 2/15/16.
 */
public class ProductService extends BaseMallBDService {

    private ResponseStat responseStat;
    ArrayList<Products> newProductsArrayList;

    public ArrayList<Products> getNewProducts(String offset, String limit){

        this.responseStat = new ResponseStat();
        this.newProductsArrayList = new ArrayList<>();

        this.setController("api/products/all/show");
        this.setParams("offset", offset);
        this.setParams("limit",limit);
        this.setParams("shop_id", String.valueOf(shop_id));

        String resp = this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status)
            {


                Log.i("Check", "true");
                Products[] products = gson.fromJson(jsonObject.get("responseData"),Products[].class);
                Collections.addAll(this.newProductsArrayList, products);
                return this.newProductsArrayList;
            }
            else {
                Utility.responseStat = this.responseStat;
                return newProductsArrayList;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return this.newProductsArrayList;

    }

    public ArrayList<Products> getCategoryWiseProducts(String offset,String limit,String categoryId) {

        ResponseStat responseStat;
        responseStat = new ResponseStat();
        ArrayList<Products> categoryWiseProducts = new ArrayList<>();

        this.setController("api/product/category");
        this.setParams("offset", offset);
        this.setParams("limit", limit);
        this.setParams("shop_id", String.valueOf(shop_id));
       // this.setParams("category",categoryId);
        this.setParams("category",String.valueOf(16));

        String resp = this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());
            if (this.responseStat.status) {

                Log.i("Check", "true");
                Products[] products = gson.fromJson(jsonObject.get("responseData"), Products[].class);
                Collections.addAll(categoryWiseProducts, products);
                return categoryWiseProducts;
            } else {
                Utility.responseStat = responseStat;
                return categoryWiseProducts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryWiseProducts;
    }
}
