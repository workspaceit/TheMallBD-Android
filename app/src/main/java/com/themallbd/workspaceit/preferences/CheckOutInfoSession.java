package com.themallbd.workspaceit.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Mausum on 4/6/2016.
 */
public class CheckOutInfoSession {

    SharedPreferences pref;
    Editor editor;
    Context _context;
    final int PRIVATE_MODE = 0;



    private static final String PREF_NAME = "checkOutInfo";

    private static final String PREF_ISSET = "isset";

    private static final String PREF_EMAIL = "email";
    private static final String PREF_FNAME = "fname";
    private static final String PREF_LNAME = "lname";
    private static final String PREF_COUNTRY = "country";
    private static final String PREF_COUNTRY_POSITION = "countryPosition";
    private static final String PREF_ADDRESS = "address";
    private static final String PREF_TELEPHONE = "telephone";

    private static final String PREF_GIFT_LAST_NAME="gift_lname";
    private static final String PREF_GIFT_FIRST_NAME="gift_lname";
    private static final String PREF_GIFT_TELEPHONE="gift_telephone";
    private static final String PREF_GIFT_ADDRESS="gift_address";
    private static final String PREF_GIFT_CITY="gift_city";

    private static final String PREF_GIFT_ADDRESS_ISSET="gift_isset";

    public CheckOutInfoSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public void saveDataInPreference(String email,String fname,String lname,String country, int countryPosition, String address, String telephone){
        editor.putBoolean(PREF_ISSET,true);

        editor.putString(PREF_EMAIL,email);
        editor.putString(PREF_FNAME,fname);
        editor.putString(PREF_LNAME,lname);
        editor.putString(PREF_COUNTRY,country);
        editor.putInt(PREF_COUNTRY_POSITION, countryPosition);
        editor.putString(PREF_ADDRESS, address);
        editor.putString(PREF_TELEPHONE,telephone);

        editor.commit();

    }

    public void setGiftAddress( String giftFname,String giftLastName,String giftCity,String giftAddress,String giftTelephone){
        editor.putBoolean(PREF_GIFT_ADDRESS_ISSET,true);
        editor.putString(PREF_GIFT_FIRST_NAME,giftFname);
        editor.putString(PREF_GIFT_LAST_NAME,giftLastName);
        editor.putString(PREF_GIFT_ADDRESS,giftAddress);
        editor.putString(PREF_GIFT_TELEPHONE,giftTelephone);
        editor.putString(PREF_GIFT_CITY,giftCity);
        editor.commit();
    }

    public boolean checkIsset(){
        return pref.getBoolean(PREF_ISSET,false);
    }

    public String getEmail(){
        return pref.getString(PREF_EMAIL, "");
    }

    public String getFname(){
        return pref.getString(PREF_FNAME,"");
    }

    public String getLname(){
        return pref.getString(PREF_LNAME,"");
    }

    public String getCity(){
        return pref.getString(PREF_COUNTRY,"");
    }

    public int getCityPosition(){
        return pref.getInt(PREF_COUNTRY_POSITION, 0);
    }

    public String getAddress(){
        return pref.getString(PREF_ADDRESS,"");
    }

    public String getTelephone(){
        return pref.getString(PREF_TELEPHONE,"");
    }

    public void setGiftIsset(boolean flag){
        editor.putBoolean(PREF_GIFT_ADDRESS_ISSET,flag);
        editor.commit();
    }

    public boolean checkGiftIsset(){
        return pref.getBoolean(PREF_GIFT_ADDRESS_ISSET,false);
    }

    public String getGiftFname(){
        return pref.getString(PREF_GIFT_FIRST_NAME,"");
    }

    public String getPrefGiftLastName(){
        return pref.getString(PREF_GIFT_LAST_NAME,"");
    }

    public String getPrefGiftTelephone(){
        return pref.getString(PREF_GIFT_TELEPHONE,"");
    }

    public String getPrefGiftCity(){
        return pref.getString(PREF_GIFT_CITY,"");

    }
    public String getPrefGiftAddressIsset(){
        return pref.getString(PREF_GIFT_ADDRESS,"");
    }
}
