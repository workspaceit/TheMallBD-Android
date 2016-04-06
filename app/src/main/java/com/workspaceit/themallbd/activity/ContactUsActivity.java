package com.workspaceit.themallbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workspaceit.themallbd.R;

public class ContactUsActivity extends BaseActivityWithoutDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Contact us");
    }
}
