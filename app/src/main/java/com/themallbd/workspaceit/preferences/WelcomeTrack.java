package com.themallbd.workspaceit.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tomal on 9/19/2016.
 */
public class WelcomeTrack {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "banner_image";

    private final String FIRST_TIME="first_time";


    public WelcomeTrack(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeTrack(boolean flag){
        editor.putBoolean(FIRST_TIME,flag);
        editor.commit();
    }

    public boolean getFirstTimeStatus(){
       return pref.getBoolean(FIRST_TIME,false);
    }
}
