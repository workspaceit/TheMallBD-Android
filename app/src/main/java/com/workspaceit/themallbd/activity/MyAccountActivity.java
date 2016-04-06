package com.workspaceit.themallbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.workspaceit.themallbd.R;

public class MyAccountActivity extends BaseActivityWithoutDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Mallbd");
    }
}
