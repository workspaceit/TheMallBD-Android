package com.workspaceit.themallbd.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mausum on 4/19/2016.
 */
public class GetSearchProductService extends BaseMallBDService {
    private ResponseStat responseStat;

    public ArrayList<Products> getSearcProduct(String keyword, String shopID, String offset, String limit){
        ArrayList<Products>searchProdut=new ArrayList<>();
        this.responseStat=new ResponseStat();
        this.setController("api/products/search");
        this.setParams("keywords", keyword);
        this.setParams("shop_id",shopID);
        this.setParams("limit",limit);
        this.setParams("offset",offset);
        String resp=this.getData("POST");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if(this.responseStat.status){

                Products[] products = gson.fromJson(jsonObject.get("responseData"),Products[].class);
                Collections.addAll(searchProdut,products);
                Collections.addAll(Utility.searchProductArrayList,products);
                return searchProdut;
            }else {
                Utility.responseStat = this.responseStat;
                return searchProdut;
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return searchProdut;

    }
}
