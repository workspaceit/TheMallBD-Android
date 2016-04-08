package com.workspaceit.themallbd.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.LoginActivity;

/**
 * Created by Mausum on 4/5/2016.
 */
public class CustomDialog {
    public static void showDailog(final Context context,String title, String body){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("Go To Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent loginIntent=new Intent(context, LoginActivity.class);
                        context.startActivity(loginIntent);


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();


    }

    public static void logoutDailog(final Context context, final SessionManager sessionManager,String title, String body){


        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("Confrim Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionManager.logoutUser();
                        MakeToast.showToast(context,"You are successfully logged out");


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }
}
