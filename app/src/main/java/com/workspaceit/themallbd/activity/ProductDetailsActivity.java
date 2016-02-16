package com.workspaceit.themallbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.workspaceit.themallbd.MainActivity;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.dataModel.Products;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    private static int position;
    private static int arrayListIndicator = 0;
    private Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        position = getIntent().getIntExtra("position", -1);
        arrayListIndicator = getIntent().getIntExtra("productArray",-1);
        if (arrayListIndicator==1)
            products = MainActivity.newProductsForHorizontalViewList.get(position);
        else if (arrayListIndicator==2)
            products = MainActivity.featuredProductsForHorizontalViewList.get(position);
        else if (arrayListIndicator==3)
            products = MainActivity.allProductsForGridViewList.get(position);


        Toast.makeText(this, products.title+products.prices.get(0).retailPrice,Toast.LENGTH_LONG).show();
    }
}
