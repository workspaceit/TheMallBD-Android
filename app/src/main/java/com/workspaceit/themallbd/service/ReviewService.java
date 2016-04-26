package com.workspaceit.themallbd.service;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.dataModel.Review;
import com.workspaceit.themallbd.utility.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mausum on 4/26/2016.
 */
public class ReviewService extends BaseMallBDService{

    private ResponseStat responseStat;

    public ArrayList<Review> getReviewList(String productId,String limit, String offset){
        ArrayList<Review>reviewsList=new ArrayList<>();
        this.responseStat=new ResponseStat();
        this.setController("api/product/review");
        this.setParams("product_id", productId);
        this.setParams("limit",limit);
        this.setParams("offset",offset);

        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){

                Review[] reviews = gson.fromJson(jsonObject.get("responseData"), Review[].class);
                Collections.addAll(reviewsList,reviews);

                return reviewsList;
            }else {
                Utility.responseStat = this.responseStat;
                return reviewsList;
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return reviewsList;
    }


    public Integer getReviewCount(String productId){

        this.responseStat=new ResponseStat();
        this.setController("api/review/count");
        this.setParams("product_id", productId);


        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){

                JsonElement jelement = new JsonParser().parse(String.valueOf(jsonObject.get("responseData")));

                int count=Integer.parseInt(jelement.toString());

                return count;
            }else {
                Utility.responseStat = this.responseStat;
                return 0;
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return 0;
    }
}
