package com.themallbd.workspaceit.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.activity.LoginActivity;
import com.themallbd.workspaceit.asynctask.AddNewReviewAsynTask;

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
                        MakeToast.showToast(context, "You are successfully logged out");


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }

    public static void showImageDiolog(final Context context, final String uri,String pTitle){
        int x,y;
        if(context.getResources().getConfiguration().orientation==1){
            x=500;
            y=700;
        }else {
            x=900;
            y=500;
        }

        ImageView image = new ImageView(context);
        Picasso.with(context)
                .load(Uri.parse(uri))
                .resize(x,y)
                .centerCrop()
                .into(image);

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context,android.R.style.Theme_Material_Light_Dialog_Alert);
        alertDialogBuilder.setTitle(pTitle);
        alertDialogBuilder
                .setCancelable(true)
                .setView(image)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();


                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();


    }

public static void addReviewCustomDailog(final Context context,String title, final String productId){
    final Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.add_review_custom_dialog);
    TextView titleTV = (TextView)dialog.findViewById(R.id.rateHeader);
    final TextView rateTV = (TextView)dialog.findViewById(R.id.rateTV);

    Button submitBtn = (Button)dialog.findViewById(R.id.submitRateBtn);
    Button cancelBtn = (Button)dialog.findViewById(R.id.cancelRateBtn);
    final RatingBar ratingBar = (RatingBar)dialog.findViewById(R.id.ratingsBar);
    LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
    stars.getDrawable(2).setColorFilter(Color.parseColor("#961C1E"), PorterDuff.Mode.SRC_ATOP);

    final EditText reviewED = (EditText)dialog.findViewById(R.id.reviewED);
    titleTV.setText(title);
    dialog.show();

    cancelBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    });

    submitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(reviewED.getText().toString().equals("")){
                MakeToast.showToast(context,"Please add some review");
            }else {
                dialog.dismiss();
                new AddNewReviewAsynTask(context).execute(productId,reviewED.getText().toString(),String.valueOf(ratingBar.getRating()));
            }

        }
    });

    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            rateTV.setText(String.valueOf(rating));
        }
    });

}
}
