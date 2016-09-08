package com.themallbd.workspaceit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class ProductByManufracturerActivity extends BaseActivityWithoutDrawer {

    private int manufracturerId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_manufracturer);

        manufracturerId=getIntent().getIntExtra("manufacture_id",0);
        MakeToast.showToast(this,manufracturerId+"");
    }
}
