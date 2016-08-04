package com.themallbd.workspaceit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.themallbd.workspaceit.dataModel.Orders;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class OrderHistoryDetailsPage extends BaseActivityWithoutDrawer {
    private Toolbar toolbar;
    private TextView toolBarTitle;
    int position=0;
    private Orders order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Order Details");

        position = getIntent().getIntExtra("position", -1);
        order=PrevoiusOrderActivity.ordersArrayList.get(position);
        MakeToast.showToast(this,order.orderProducts.size()+"");


    }
}
