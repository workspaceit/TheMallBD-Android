package com.workspaceit.themallbd.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.ResponseStat;

import com.workspaceit.themallbd.dataModel.SearchResult;
import com.workspaceit.themallbd.utility.Utility;

import org.json.JSONObject;

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
        this.setController("product/category/searchbytitle/mobile");
        this.setParams("keyword", keyword);


        String resp = this.getData("POST");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());

            if (this.responseStat.status) {
                JsonElement jelement = new JsonParser().parse(String.valueOf(jsonObject.get("responseData")));
                JsonObject jobject = jelement.getAsJsonObject();


                SearchResult[] searchResults = gson.fromJson(jobject.get("category"), SearchResult[].class);


                Log.v("tomal", String.valueOf(searchResults.length));

                for (int i = 0; i < searchResults.length; i++) {
                    productTitles.add(searchResults[i].product_title);
                    Log.v("taiful", productTitles.get(i));

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
        Log.v("taiful",limit+" "+offset+" product ID: "+productId+" categoryId: "+categoryId);
        String resp = this.getData("POST");
        System.out.println("related :"+resp);
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
}
