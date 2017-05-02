package com.themallbd.workspaceit.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * Created by rajib on 2/15/16.
 */
public class InternetConnection {

    public Context context;

    public InternetConnection(Context context) {
        this.context = context;
    }



    public boolean checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo==null)
            return false;
        else {
            if (netInfo.isConnectedOrConnecting()){
                return true;
            }else {
                return false;
            }

        }
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
}