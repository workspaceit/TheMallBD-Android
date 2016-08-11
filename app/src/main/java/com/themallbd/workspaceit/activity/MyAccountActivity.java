package com.themallbd.workspaceit.activity;

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

import com.themallbd.workspaceit.utility.CustomDialog;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.asynctask.UpdateMyAccountAsynTask;
import com.themallbd.workspaceit.utility.CheckOutInfoSession;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.SessionManager;
import com.themallbd.workspaceit.utility.Utility;

public class MyAccountActivity extends BaseActivityWithoutDrawer implements View.OnClickListener {

    private LinearLayout accountInfoLayout;


    private EditText emailEdtText;
    private EditText fnameMyaccEditText;
    private EditText lnameMyaccEditText;
    private EditText addressAccountInfoEditext;
    private EditText phoneMyaccEditText;

    private EditText zipCodeMyaccEditText;
    private FloatingActionButton editFab;
    private FloatingActionButton lockFab;


    private Spinner counryMyaccSpinner;
    private EditText cityMyaccEditText;

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


        myaccScroll = (ScrollView) findViewById(R.id.myacc_scroll);

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


        sessionManager = new SessionManager(this);
        checkOutInfoSession = new CheckOutInfoSession(this);

        emailEdtText.setText(sessionManager.getEmail());
        fnameMyaccEditText.setText(sessionManager.getFirstName());
        lnameMyaccEditText.setText(sessionManager.getLastName());
        phoneMyaccEditText.setText(sessionManager.getPhone());
        addressAccountInfoEditext.setText(sessionManager.getAddress());
        zipCodeMyaccEditText.setText(sessionManager.getZipcode());


        accountInfoLayout = (LinearLayout) findViewById(R.id.acc_info_layout);


        counryMyaccSpinner = (Spinner) findViewById(R.id.county_myacc_spinner);
        counryMyaccSpinner.setEnabled(false);

        String country = sessionManager.getCountry();

        int index = 0;
        for (int i = 0; i < counryMyaccSpinner.getCount(); i++) {
            if (counryMyaccSpinner.getItemAtPosition(i).equals(country)) {
                index = i;
                break;
            }
        }
        counryMyaccSpinner.setSelection(index);


        cityMyaccEditText = (EditText) findViewById(R.id.city_myacc_edittext);
        this.disableEditText(cityMyaccEditText);
        cityMyaccEditText.setText(sessionManager.getCity());





    }


    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
    }

    @Override
    public void onClick(View v) {

        if (v == editFab) {


            if (editFabFlag == 0) {
                myaccScroll.smoothScrollTo(0, 0);
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

                if (this.checkInputValidity()) {
                    new UpdateMyAccountAsynTask(this).execute(fnameMyaccEditText.getText().toString(), lnameMyaccEditText.getText().toString(),
                            phoneMyaccEditText.getText().toString(), addressAccountInfoEditext.getText().toString(), counryMyaccSpinner.getSelectedItem().toString(),
                            cityMyaccEditText.getText().toString(), zipCodeMyaccEditText.getText().toString());

                    editFab.setImageDrawable(getResources().getDrawable(R.drawable.pencil));
                }

            }


        }else if (v==lockFab){
            CustomDialog.resetPasswordCustomDaiog(this);
        }
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

    public void restFab() {
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

        String country = counryMyaccSpinner.getSelectedItem().toString();
        if (country.equals("Country")) {
            country = "";
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
