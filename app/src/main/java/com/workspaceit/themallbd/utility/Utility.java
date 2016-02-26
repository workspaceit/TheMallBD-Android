package com.workspaceit.themallbd.utility;

import com.workspaceit.themallbd.dataModel.AppCredential;
import com.workspaceit.themallbd.dataModel.ResponseStat;
import com.workspaceit.themallbd.dataModel.ShoppingCart;
import com.workspaceit.themallbd.dataModel.User;

/**
 * Created by rajib on 2/15/16.
 */
public class Utility {

    public static ResponseStat responseStat = new ResponseStat();
    public static AppCredential loggedInUser = new AppCredential();
    public static String IMAGE_URL = "http://27.147.149.178:5555/mallbackoffice/resources/images/";
    //public static  String IMAGE_URL = "http://192.168.1.11/mallbdweb/public/product_images/";

    public static ShoppingCart shoppingCart = new ShoppingCart();
}
