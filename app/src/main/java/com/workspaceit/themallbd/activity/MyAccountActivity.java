package com.workspaceit.themallbd.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.asynctask.UpdateMyAccountAsynTask;
import com.workspaceit.themallbd.utility.CheckOutInfoSession;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.SessionManager;
import com.workspaceit.themallbd.utility.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAccountActivity extends BaseActivityWithoutDrawer implements View.OnClickListener {

    private LinearLayout accountInfoLayout;
    private LinearLayout shippingInfoLyout;
    private Button exapandAccountInfoButton;
    private Button expandShippingInfoButton;

    private EditText emailEdtText;
    private EditText fnameMyaccEditText;
    private EditText lnameMyaccEditText;
    private EditText addressAccountInfoEditext;
    private EditText phoneMyaccEditText;

    private EditText zipCodeMyaccEditText;
    private FloatingActionButton editFab;
    private FloatingActionButton lockFab;
    private Button addNewShippingInfoButton;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText shippingAdresstext;
    private Spinner counryMyaccSpinner;
    private EditText cityMyaccEditText;
    private int imageUpArrow;
    private int imageDownArrow;
    private ScrollView myaccScroll;


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

        myaccScroll=(ScrollView)findViewById(R.id.myacc_scroll);

        emailEdtText = (EditText) findViewById(R.id.email_myacc_edittext);
        this.disableEditText(emailEdtText);

        fnameMyaccEditText = (EditText) findViewById(R.id.fname_myacc_edittext);
        this.disableEditText(fnameMyaccEditText);

        lnameMyaccEditText = (EditText) findViewById(R.id.lname_myacc_edittext);
        this.disableEditText(lnameMyaccEditText);

        phoneMyaccEditText = (EditText) findViewById(R.id.phone_myacc_edittext);
        this.disableEditText(phoneMyaccEditText);


        addressAccountInfoEditext = (EditText) findViewById(R.id.address_myacc_edittext);
        this.disableEditText(addressAccountInfoEditext);

        zipCodeMyaccEditText = (EditText) findViewById(R.id.zipcoe_myacc_edittext);
        this.disableEditText(zipCodeMyaccEditText);

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

        emailEdtText.setText(sessionManager.getEmail());
        fnameMyaccEditText.setText(sessionManager.getFirstName());
        lnameMyaccEditText.setText(sessionManager.getLastName());
        phoneMyaccEditText.setText(sessionManager.getPhone());
        addressAccountInfoEditext.setText(sessionManager.getAddress());
        zipCodeMyaccEditText.setText(sessionManager.getZipcode());


        firstNameText.setText(checkOutInfoSession.getFname());
        lastNameText.setText(checkOutInfoSession.getLname());
        shippingAdresstext.setText(checkOutInfoSession.getAddress());


        accountInfoLayout = (LinearLayout) findViewById(R.id.acc_info_layout);
        shippingInfoLyout = (LinearLayout) findViewById(R.id.shipping_info_layout);

        exapandAccountInfoButton = (Button) findViewById(R.id.acc_info_expand_button);
        exapandAccountInfoButton.setOnClickListener(this);

        expandShippingInfoButton = (Button) findViewById(R.id.shipping_address_expand_button);
        expandShippingInfoButton.setOnClickListener(this);

        counryMyaccSpinner=(Spinner)findViewById(R.id.county_myacc_spinner);
        counryMyaccSpinner.setEnabled(false);

        String country=sessionManager.getCountry();

        int index=0;
        for(int i=0; i<counryMyaccSpinner.getCount();i++){
            if(counryMyaccSpinner.getItemAtPosition(i).equals(country)){
                index=i;
                break;
            }
        }
        counryMyaccSpinner.setSelection(index);



        cityMyaccEditText=(EditText)findViewById(R.id.city_myacc_edittext);
        this.disableEditText(cityMyaccEditText);
        cityMyaccEditText.setText(sessionManager.getCity());


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
                    expandShippingInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageDownArrow,0);
                }
                this.showLayout(accountInfoLayout, 500);
            }
        } else if (v == expandShippingInfoButton) {

            if (shippingInfoLyout.getVisibility() == View.VISIBLE) {
                expandShippingInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                this.hideLayout(shippingInfoLyout, 500);

            } else {
                expandShippingInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);
                if (accountInfoLayout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(accountInfoLayout, 100);
                    exapandAccountInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageDownArrow,0);
                }

                this.showLayout(shippingInfoLyout, 500);

            }
        } else if (v == editFab) {


            if (editFabFlag == 0) {
                myaccScroll.smoothScrollTo(0,0);
                fnameMyaccEditText.requestFocus();
                fnameMyaccEditText.setEnabled(true);
                lnameMyaccEditText.setEnabled(true);
                phoneMyaccEditText.setEnabled(true);
                addressAccountInfoEditext.setEnabled(true);
                counryMyaccSpinner.setEnabled(true);
                cityMyaccEditText.setEnabled(true);
                zipCodeMyaccEditText.setEnabled(true);
                editFab.setImageDrawable(getResources().getDrawable(R.drawable.save));

                lockFab.setEnabled(false);
                lockFab.setAlpha(0.5F);
                editFabFlag = 2;
            } else if (editFabFlag == 1) {
                editFab.setImageDrawable(getResources().getDrawable(R.drawable.pencil));
                lockFab.setEnabled(true);
                lockFab.setAlpha(1.0F);
                editFabFlag = 0;
            } else if (editFabFlag == 2) {

                if(this.checkInputValidity()){
                   new UpdateMyAccountAsynTask(this).execute(fnameMyaccEditText.getText().toString(),lnameMyaccEditText.getText().toString(),
                           phoneMyaccEditText.getText().toString(),addressAccountInfoEditext.getText().toString(),counryMyaccSpinner.getSelectedItem().toString(),
                           cityMyaccEditText.getText().toString(),zipCodeMyaccEditText.getText().toString());

                    editFab.setImageDrawable(getResources().getDrawable(R.drawable.pencil));
                }

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


    private boolean checkInputValidity() {



        if (fnameMyaccEditText.getText().toString().equals("")) {
            MakeToast.showToast(this, "First Name is Required");
            fnameMyaccEditText.requestFocus();
            return false;
        } else if (lnameMyaccEditText.getText().toString().equals("")) {
            MakeToast.showToast(this, "Last Name is Required");
            lnameMyaccEditText.requestFocus();
            return false;

        } else if (phoneMyaccEditText.getText().toString().equals("")) {
            MakeToast.showToast(this, "Telephone Number is required");
            phoneMyaccEditText.requestFocus();
            return false;

        }

        return true;
    }

    public void restFab(){
        editFab.setImageDrawable(getResources().getDrawable(R.drawable.pencil));
        lockFab.setEnabled(true);
        lockFab.setAlpha(1.0F);
        editFabFlag = 0;
        fnameMyaccEditText.setEnabled(false);
        lnameMyaccEditText.setEnabled(false);
        phoneMyaccEditText.setEnabled(false);
        addressAccountInfoEditext.setEnabled(false);
        counryMyaccSpinner.setEnabled(false);
        cityMyaccEditText.setEnabled(false);
        zipCodeMyaccEditText.setEnabled(false);

        String country=counryMyaccSpinner.getSelectedItem().toString();
        if(country.equals("Country")) {
            country="";
        }

        sessionManager.updateUserInformation(fnameMyaccEditText.getText().toString(), lnameMyaccEditText.getText().toString(),
                phoneMyaccEditText.getText().toString(), addressAccountInfoEditext.getText().toString(), country,
                cityMyaccEditText.getText().toString(), zipCodeMyaccEditText.getText().toString());

        fnameMyaccEditText.setText(Utility.loggedInUser.user.firstName);
        lnameMyaccEditText.setText(Utility.loggedInUser.user.lastName);
        phoneMyaccEditText.setText(Utility.loggedInUser.user.phone);
        addressAccountInfoEditext.setText(Utility.loggedInUser.user.userDetails.address.address);
        cityMyaccEditText.setText(Utility.loggedInUser.user.userDetails.address.city);
        zipCodeMyaccEditText.setText(Utility.loggedInUser.user.userDetails.address.zipCode);


    }


}
