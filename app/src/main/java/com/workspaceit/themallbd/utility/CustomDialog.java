package com.workspaceit.themallbd.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.workspaceit.themallbd.R;

/**
 * Created by Mausum on 4/5/2016.
 */
public class CustomDialog {
    public static void showDailog(Context context,String title, String body){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();


    }
}
