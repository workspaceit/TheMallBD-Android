package com.themallbd.workspaceit.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tomal on 7/21/2016.
 */
public class LocalShoppintCart {
    private SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "cart";

    private final String PRODUCT_CART_JSON="product_cart";
    private final String PACKAGE_CART_JSON="package_cart";


    public LocalShoppintCart(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setProductCart(String cart){
        editor.putString(PRODUCT_CART_JSON,cart);
        editor.commit();

    }

    public String getProductCart(){
        return pref.getString(PRODUCT_CART_JSON,"");
    }

    public void setPackageCart(String cart){
        editor.putString(PACKAGE_CART_JSON,cart);
        editor.commit();
    }

    public String getPackageCart(){
        return pref.getString(PACKAGE_CART_JSON,"");
    }
}
