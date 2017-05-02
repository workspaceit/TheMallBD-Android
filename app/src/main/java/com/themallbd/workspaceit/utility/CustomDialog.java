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
import com.themallbd.workspaceit.activity.BKashPaymentActivity;
import com.themallbd.workspaceit.activity.BaseActivity;
import com.themallbd.workspaceit.activity.CheckoutActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.activity.PaypalPaymentActivity;
import com.themallbd.workspaceit.activity.PrevoiusOrderActivity;
import com.themallbd.workspaceit.activity.WalletMixPaymentWebViewActivity;
import com.themallbd.workspaceit.asynctask.ChangePasswordAsynTask;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.activity.LoginActivity;
import com.themallbd.workspaceit.asynctask.AddNewReviewAsynTask;

/**
 * Created by Mausum on 4/5/2016.
 */
public class CustomDialog {

    public static void goToCheckOutDailog(final Context context,String title,String body){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setCancelable(true)
                .setMessage(body)
                .setPositiveButton("Got to Checkout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(context, CheckoutActivity.class);
                        context.startActivity(intent);

                    }
                }).setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

    }

    public static void BkashPaymentDialog(final Context context,String title,String body, final String transId){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setCancelable(false)
                .setMessage(body)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(context, BKashPaymentActivity.class);
                        intent.putExtra("trnx_Id", transId);
                        context.startActivity(intent);

                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

    }


    public static void paypalPayment(final Context context,String title, String body){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setCancelable(false)
                .setMessage(body)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent=new Intent(context, PaypalPaymentActivity.class);
                        context.startActivity(intent);

                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }

    public static void walletMixPayment(final Context context,String title, String body, final int orderId){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setCancelable(false)
                .setMessage(body)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent=new Intent(context, WalletMixPaymentWebViewActivity.class);
                        intent.putExtra("order_id",orderId);
                        context.startActivity(intent);

                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }

    public static void orderPlaceWithoutLogin(final Context context,String title,String body){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("Continue Shopping", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }

    public static void orderPlaceDiolog(final Context context,String title,String body){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("Prevoius Order", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent orderIntent = new Intent(context, PrevoiusOrderActivity.class);
                        context.startActivity(orderIntent);


                    }
                }).setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

    }

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
                .setPositiveButton("Confirm Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionManager.logoutUser();
                        Utility.isLoggedInFlag = false;
                        Utility.loggedInUser = null;
                        MakeToast.showToast(context, "You are successfully logged out");
                        if (context instanceof BaseActivity) {
                            ((BaseActivity)context).hideNShowLoginButton();
                        }


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

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
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
            if (reviewED.getText().toString().equals("")) {
                MakeToast.showToast(context, "Please add some review");
            } else {
                dialog.dismiss();
                new AddNewReviewAsynTask(context).execute(productId, reviewED.getText().toString(), String.valueOf(ratingBar.getRating()));
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


    public static void resetPasswordCustomDaiog(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reset_password_custom_dialog);
        final EditText oldPasswordEditText=(EditText)dialog.findViewById(R.id.old_password_reset_edit_text);
        final EditText newPasswordEdiTexr=(EditText)dialog.findViewById(R.id.new_password_reset_edit_text);
        final EditText confirmEditText=(EditText)dialog.findViewById(R.id.confirm_password_reset_edit_text);
        final Button changePasswordButton=(Button)dialog.findViewById(R.id.reset_password_button);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPasswordEditText.getText().toString().equals("") || oldPasswordEditText.getText().length()<6){
                    MakeToast.showToast(context,"Old Password is too short");
                }else if (newPasswordEdiTexr.getText().equals("")||newPasswordEdiTexr.getText().length()<6){
                    MakeToast.showToast(context,"New Password must be of at least 6 characters");
                }else if (!confirmEditText.getText().toString().equals(newPasswordEdiTexr.getText().toString())){
                    MakeToast.showToast(context,"Confirm password mismatched");
                }else {
                    new ChangePasswordAsynTask(context).execute(oldPasswordEditText.getText().toString(),newPasswordEdiTexr.getText().toString());
                    dialog.cancel();

                }
            }
        });
        dialog.show();

    }
}
