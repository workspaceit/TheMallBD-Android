package com.workspaceit.themallbd.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by rajib on 11/28/15.
 */
public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "users";


    // All Shared Preferences Keys
    private final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    private final String KEY_Access = "access_key";
    private final String KEY_REGISTRATION = "registration_key";


    // Email address (make variable public to access from outside)
    private final String KEY_EMAIL = "email";
    private final String KEY_UID = "uid";
    private final String KEY_USER_NAME = "user_full_name";
    private final String KEY_PROFILE_IMAGE_PATH="image_path";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }


    public void createLoginSession(String access_key, String email, int uid, String fullname) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_Access, access_key);
        editor.putString(KEY_USER_NAME, fullname);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_UID, uid);

        editor.commit();

    }

    public void saveProfileImageUri(String path){
        editor.putString(KEY_PROFILE_IMAGE_PATH,path);
        editor.commit();
    }

    public String getProfileImageUri(){

        return pref.getString(KEY_PROFILE_IMAGE_PATH,"");
    }

    public void saveRegisterId(String id) {
        editor.putString(KEY_REGISTRATION, id);

        editor.commit();

    }

    public int getUid() {
        return pref.getInt(KEY_UID, 0);
    }

    public String getAccessToken() {

        return pref.getString(KEY_Access, "");
    }

    public String getKeyRegistration() {

        return pref.getString(KEY_REGISTRATION, "");
    }


    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public boolean checkLogin() {
        // Check login status
        return this.isLoggedIn();

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getFullName() {
        return pref.getString(KEY_USER_NAME, "");
    }
}

