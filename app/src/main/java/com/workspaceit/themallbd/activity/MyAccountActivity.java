package com.workspaceit.themallbd.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.MakeToast;

public class MyAccountActivity extends BaseActivityWithoutDrawer implements View.OnClickListener {

    private LinearLayout accountInfoLayout;
    private LinearLayout shippingInfoLyout;
    private Button exapandAccountInfoButton;
    private Button expandShippingInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Mallbd");

        accountInfoLayout=(LinearLayout)findViewById(R.id.acc_info_layout);
        shippingInfoLyout=(LinearLayout)findViewById(R.id.shipping_info_layout);

        exapandAccountInfoButton =(Button)findViewById(R.id.acc_info_expand_button);
        exapandAccountInfoButton.setOnClickListener(this);

        expandShippingInfoButton=(Button)findViewById(R.id.shipping_address_expand_button);
        expandShippingInfoButton.setOnClickListener(this);


        accountInfoLayout.setVisibility(View.GONE);
        shippingInfoLyout.setVisibility(View.GONE);




    }

    @Override
    public void onClick(View v) {
        if(v== exapandAccountInfoButton){
           if(accountInfoLayout.getVisibility()==View.VISIBLE) {

               accountInfoLayout.animate()
                       .translationY(0)
                       .setDuration(300)
                       .setListener(new AnimatorListenerAdapter() {
                           @Override
                           public void onAnimationEnd(Animator animation) {
                               super.onAnimationEnd(animation);
                               accountInfoLayout.setVisibility(View.GONE);
                           }
                       });



           }else {


               accountInfoLayout.animate()
                       .alphaBy(1.0F)
                       .setDuration(300)
                       .setListener(new AnimatorListenerAdapter() {
                           @Override
                           public void onAnimationStart(Animator animation) {
                               super.onAnimationStart(animation);
                               shippingInfoLyout.setVisibility(View.GONE);
                               accountInfoLayout.setVisibility(View.VISIBLE);
                           }
                       });



           }
        }else if(v==expandShippingInfoButton){

            if(shippingInfoLyout.getVisibility()==View.VISIBLE){
                shippingInfoLyout.animate()

                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                shippingInfoLyout.setVisibility(View.GONE);

                            }
                        });

            }else {

                shippingInfoLyout.animate()
                        .alphaBy(1.0F)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                accountInfoLayout.setVisibility(View.GONE);
                                shippingInfoLyout.setVisibility(View.VISIBLE);
                            }
                        });

            }
        }
    }
}
