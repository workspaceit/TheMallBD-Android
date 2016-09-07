package com.themallbd.workspaceit.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tomal on 9/6/2016.
 */
public class LocalNewProduct {
   private SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "category";

    private final String CATEGORY_FULL_JSON="product_cart";
    private final String CATEGORY_SET_FLAG="category_flag";



    public LocalNewProduct(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

}
