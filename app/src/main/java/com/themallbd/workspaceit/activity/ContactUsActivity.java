package com.themallbd.workspaceit.activity;

import android.os.Bundle;

import com.workspaceit.themall.R;

public class ContactUsActivity extends BaseActivityWithoutDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Contact us");
    }
}
