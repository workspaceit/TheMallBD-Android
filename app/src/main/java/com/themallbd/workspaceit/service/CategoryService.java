package com.themallbd.workspaceit.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.themallbd.workspaceit.dataModel.Category;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rajib on 2/23/16.
 */
public class CategoryService extends BaseMallBDService {

    private ResponseStat responseStat;

    public ArrayList<Category> getChildCategoryByParentId(String parentId){
        ArrayList<Category> newCategoryArraylist = new ArrayList<>();
        this.responseStat = new ResponseStat();

        this.setParams("shop_id", "1");

        if (parentId.equals("0"))
        {
            this.setController("api/category/parents/show");
        }
        else {
            this.setController("api/category/childs/show");
            this.setParams("parent_id",parentId);
        }

        String resp = this.getData("POST");


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status)
            {

                Category[] categories = gson.fromJson(jsonObject.get("responseData"),Category[].class);
                Collections.addAll(newCategoryArraylist, categories);
                return newCategoryArraylist;
            }
            else {
                Utility.responseStat = this.responseStat;
                return newCategoryArraylist;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return newCategoryArraylist;
    }

    public ArrayList<Category> getParentsCategories(){

        ArrayList<Category> newCategoryArraylist = new ArrayList<>();
        this.responseStat = new ResponseStat();

        this.setParams("shop_id", "1");
        this.setController("api/category/parents/show");

        String resp = this.getData("POST");


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.status)
            {
                Log.i("Check", "true");
                Category[] categories = gson.fromJson(jsonObject.get("responseData"),Category[].class);
                Collections.addAll(newCategoryArraylist, categories);
                Collections.addAll(Utility.parentsCategoryArraylist,categories);
                return newCategoryArraylist;
            }
            else {
                Utility.responseStat = this.responseStat;
                return newCategoryArraylist;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return newCategoryArraylist;

    }
}
