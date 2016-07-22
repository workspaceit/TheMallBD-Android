package com.themallbd.workspaceit.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class NoInternetActiviy extends AppCompatActivity {
    private Button netButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_activiy);

        netButton=(Button)findViewById(R.id.internet_button);

        netButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null) {
                    MakeToast.showToast(getApplicationContext(), "No Internet Connection");
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}
