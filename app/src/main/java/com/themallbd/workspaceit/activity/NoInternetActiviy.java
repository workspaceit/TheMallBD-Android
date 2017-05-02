package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class NoInternetActiviy extends AppCompatActivity {
    private Button netButton;
    private InternetConnection internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_activiy);

        netButton=(Button)findViewById(R.id.internet_button);
        internetConnection=new InternetConnection(this);

        netButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internetConnection.checkInternet()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    MakeToast.showToast(getApplicationContext(), "No Internet Connection");

                }

            }
        });

    }
}
