package com.themallbd.workspaceit.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.activity.SearchProductListActivity;
import com.themallbd.workspaceit.dataModel.SearchResult;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.ResponseStat;

import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mausum on 4/19/2016.
 */
public class GetSearchProductService extends BaseMallBDService {
    private ResponseStat responseStat;

    public ArrayList<String> getSearcProduct(String keyword) {
        ArrayList<String> productTitles = new ArrayList<>();
        Utility.searchProductTitle.clear();
        this.responseStat = new ResponseStat();
        this.setController("api/products/all/search/suggestion");
        this.setParams("keyword", keyword);
        this.setParams("limit","50");


        String resp = this.getData("POST");

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

                SearchResult[] searchResults = gson.fromJson(jsonObject.get("responseData"), SearchResult[].class);




                for (int i = 0; i < searchResults.length; i++) {
                    productTitles.add(searchResults[i].product_title);


                }
                Collections.addAll(Utility.searchResults, searchResults);
                Utility.searchProductTitle.addAll(productTitles);
                return productTitles;
            } else {
                Utility.responseStat = this.responseStat;
                return productTitles;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return productTitles;

    }

    public ArrayList<Products> getRelatedProduct(String limit, String offset, String productId, String categoryId) {
        ArrayList<Products> relatedProdutsArry = new ArrayList<>();
        this.responseStat = new ResponseStat();
        this.setController("api/relatedproduct/get");
        this.setParams("limit", limit);
        this.setParams("offset", offset);
        this.setParams("product_id", productId);
        this.setParams("product_category_id", categoryId);
        Log.v("taiful", limit + " " + offset + " product ID: " + productId + " categoryId: " + categoryId);
        String resp = this.getData("POST");

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

                Products[] products = gson.fromJson(jsonObject.get("responseData"), Products[].class);


                Collections.addAll(relatedProdutsArry, products);

                return relatedProdutsArry;
            } else {
                Utility.responseStat = this.responseStat;
                return relatedProdutsArry;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return relatedProdutsArry;


    }

    public Products getProductByProductId(String productId){
        Products product=null;
        this.responseStat = new ResponseStat();
        this.setController("api/products/findbyid");
        this.setParams("id", productId);


        String resp = this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

             product = gson.fromJson(jsonObject.get("responseData"), Products.class);




                return product;
            } else {
                Utility.responseStat = this.responseStat;
                return product;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return product;


    }

    public boolean getProductArrayByKeyword(String keyword,String limit, String offset){

        ArrayList<Products> relatedProdutsArry = new ArrayList<>();
        this.responseStat = new ResponseStat();
        this.setController("api/products/all/search");
        this.setParams("keyword", keyword);
        this.setParams("limit", limit);
        this.setParams("offset", offset);
        System.out.println(keyword+" "+limit+" "+offset);
        String resp = this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {

                Products[] products = gson.fromJson(jsonObject.get("responseData"), Products[].class);


                Collections.addAll(SearchProductListActivity.searchProductArrayList, products);
                Utility.responseStat=this.responseStat;

                return true;
            } else {
                Utility.responseStat = this.responseStat;
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return true;
    }
}
