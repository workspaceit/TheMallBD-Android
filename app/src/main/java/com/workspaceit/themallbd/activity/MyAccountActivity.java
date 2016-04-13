package com.workspaceit.themallbd.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.CheckOutInfoSession;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.SessionManager;

public class MyAccountActivity extends BaseActivityWithoutDrawer implements View.OnClickListener {

    private LinearLayout accountInfoLayout;
    private LinearLayout shippingInfoLyout;
    private Button exapandAccountInfoButton;
    private Button expandShippingInfoButton;

    private EditText emailTextView;
    private EditText fnameTextView;
    private EditText addressAccountInfoEditext;
    private EditText zipCodeEditText;
    private FloatingActionButton editFab;
    private FloatingActionButton lockFab;
    private Button addNewShippingInfoButton;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText shippingAdresstext;
    private int imageUpArrow;
    private int imageDownArrow;


    SessionManager sessionManager;
    CheckOutInfoSession checkOutInfoSession;

    private short editFabFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Mallbd");

        imageDownArrow = R.drawable.arrow_down;
        imageUpArrow = R.drawable.arrow_up;

        emailTextView = (EditText) findViewById(R.id.email_myacc_edittext);
        this.disableEditText(emailTextView);

        fnameTextView = (EditText) findViewById(R.id.fname_myacc_edittext);
        this.disableEditText(fnameTextView);

        addressAccountInfoEditext = (EditText) findViewById(R.id.address_myacc_edittext);
        this.disableEditText(addressAccountInfoEditext);

        zipCodeEditText = (EditText) findViewById(R.id.zipcoe_myacc_edittext);
        this.disableEditText(zipCodeEditText);

        editFabFlag = 0;

        editFab = (FloatingActionButton) findViewById(R.id.editFab);
        editFab.setOnClickListener(this);
        lockFab = (FloatingActionButton) findViewById(R.id.lockFab);
        lockFab.setOnClickListener(this);
        addNewShippingInfoButton = (Button) findViewById(R.id.addnew_myacc_button);

        firstNameText = (EditText) findViewById(R.id.firstname_myacc_edittext);
        this.disableEditText(firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastname_myacc_edittext);
        this.disableEditText(lastNameText);

        shippingAdresstext = (EditText) findViewById(R.id.shipping_info_address_edittxet);
        this.disableEditText(shippingAdresstext);


        sessionManager = new SessionManager(this);
        checkOutInfoSession = new CheckOutInfoSession(this);

        emailTextView.setText(sessionManager.getEmail());
        fnameTextView.setText(sessionManager.getFirstName());
        addressAccountInfoEditext.setText(sessionManager.getAddress());
        zipCodeEditText.setText(sessionManager.getZipcode());
        firstNameText.setText(checkOutInfoSession.getFname());
        lastNameText.setText(checkOutInfoSession.getLname());
        shippingAdresstext.setText(checkOutInfoSession.getAddress());


        accountInfoLayout = (LinearLayout) findViewById(R.id.acc_info_layout);
        shippingInfoLyout = (LinearLayout) findViewById(R.id.shipping_info_layout);

        exapandAccountInfoButton = (Button) findViewById(R.id.acc_info_expand_button);
        exapandAccountInfoButton.setOnClickListener(this);

        expandShippingInfoButton = (Button) findViewById(R.id.shipping_address_expand_button);
        expandShippingInfoButton.setOnClickListener(this);


        accountInfoLayout.setVisibility(View.GONE);
        shippingInfoLyout.setVisibility(View.GONE);


    }


    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v == exapandAccountInfoButton) {
            if (accountInfoLayout.getVisibility() == View.VISIBLE) {

                exapandAccountInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                this.hideLayout(accountInfoLayout, 500);


            } else {

                exapandAccountInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);

                if (shippingInfoLyout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(shippingInfoLyout, 100);
                }
                this.showLayout(accountInfoLayout, 500);
            }
        } else if (v == expandShippingInfoButton) {

            if (shippingInfoLyout.getVisibility() == View.VISIBLE) {
                expandShippingInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageDownArrow,0);
                this.hideLayout(shippingInfoLyout, 500);

            } else {
                expandShippingInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageUpArrow,0);
                if (accountInfoLayout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(accountInfoLayout, 100);
                }

                this.showLayout(shippingInfoLyout, 500);

            }
        } else if (v == editFab) {


            if (editFabFlag == 0) {
                emailTextView.setEnabled(true);
                emailTextView.requestFocus();
                fnameTextView.setEnabled(true);
                addressAccountInfoEditext.setEnabled(true);
                zipCodeEditText.setEnabled(true);
                editFab.setImageDrawable(getResources().getDrawable(R.drawable.save));
                lockFab.setEnabled(false);
                lockFab.setAlpha(0.5F);
                editFabFlag = 1;
            } else if (editFabFlag == 1) {
                editFab.setImageDrawable(getResources().getDrawable(R.drawable.pencil));
                lockFab.setEnabled(true);
                lockFab.setAlpha(1.0F);
                editFabFlag = 0;
            }


        }
    }


    private void hideLayout(final LinearLayout linearLayout, int duration) {

        linearLayout.animate()
                .alpha(0.0F)
                .translationY(-linearLayout.getHeight())
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        linearLayout.setVisibility(View.GONE);
                    }
                });

    }

    private void showLayout(final LinearLayout linearLayout, int duration) {
        linearLayout.animate()
                .alphaBy(1.0F)
                .translationYBy(linearLayout.getHeight())
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                });


    }


}
