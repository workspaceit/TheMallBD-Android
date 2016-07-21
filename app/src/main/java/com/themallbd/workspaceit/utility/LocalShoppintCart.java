package com.themallbd.workspaceit.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tomal on 7/21/2016.
 */
public class LocalShoppintCart {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "cart";

    private final String CART_JSON="cart";


    public LocalShoppintCart(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setCart(String cart){
        editor.putString(CART_JSON,cart);
        editor.commit();

    }

    public String getCart(){
        return pref.getString(CART_JSON,"");
    }
}
