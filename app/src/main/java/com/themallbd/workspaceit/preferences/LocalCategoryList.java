package com.themallbd.workspaceit.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tomal on 9/6/2016.
 */
public class LocalCategoryList {

    SharedPreferences pref;

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

    public LocalCategoryList(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setCategoryList(String cart){
        editor.putString(CATEGORY_FULL_JSON,cart);
        editor.commit();
    }

    public String getCategoryList(){
        return pref.getString(CATEGORY_FULL_JSON,"");
    }

    public void setCategorySetFlag(boolean flag){
                editor.putBoolean(CATEGORY_SET_FLAG,flag);
        editor.commit();
    }

    public boolean getCategoryFlag(){
        return pref.getBoolean(CATEGORY_SET_FLAG,false);
    }
}
