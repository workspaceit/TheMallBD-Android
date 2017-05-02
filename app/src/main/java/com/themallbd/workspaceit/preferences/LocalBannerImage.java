package com.themallbd.workspaceit.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tomal on 9/6/2016.
 */
public class LocalBannerImage {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "banner_image";

    private final String BANNER_IMAGES="banner_iamge";
    private final String BANNER_FLAG="banner_flag";


    public LocalBannerImage(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setBannerList(String banner){
        editor.putString(BANNER_IMAGES,banner);
        editor.commit();
    }

    public String getBannerList(){
        return pref.getString(BANNER_IMAGES,"");
    }

    public void setBnnerFlag(boolean flag){
        editor.putBoolean(BANNER_FLAG,flag);
        editor.commit();

    }

    public boolean getBannerFlag(){
        return pref.getBoolean(BANNER_FLAG,false);
    }
}
