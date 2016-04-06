package com.workspaceit.themallbd.utility;

import com.workspaceit.themallbd.dataModel.AppCredential;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.dataModel.ShoppingCart;
import com.workspaceit.themallbd.dataModel.User;

import java.util.ArrayList;

/**
 * Created by rajib on 2/15/16.
 */
public class Utility {

    public static ResponseStat responseStat = new ResponseStat();
    public static AppCredential loggedInUser = new AppCredential();
    public static String IMAGE_URL = "http://163.53.151.2:5555/mallbackoffice/resources/images/";


    public static ShoppingCart shoppingCart = new ShoppingCart();

    public static ArrayList<Category> parentsCategoryArraylist = new ArrayList<>();
}
