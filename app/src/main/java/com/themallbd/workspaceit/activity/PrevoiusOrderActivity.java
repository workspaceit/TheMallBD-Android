package com.themallbd.workspaceit.activity;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.OrderHistoryAdapter;

import com.themallbd.workspaceit.asynctask.GetPreViousOrderProductAsyncTask;
import com.themallbd.workspaceit.dataModel.Orders;
import com.themallbd.workspaceit.service.InternetConnection;

import com.workspaceit.themall.R;

import java.util.ArrayList;

public class PrevoiusOrderActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private TextView toolBarTitle;
    public static ArrayList<Orders> ordersArrayList = new ArrayList<>();
    private ListView orederHistoryListView;
    private OrderHistoryAdapter orderHistoryAdapter;
    private InternetConnection mInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevoius_order);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Your Order History");

        orederHistoryListView = (ListView) findViewById(R.id.all_order_history_list_view);
        orderHistoryAdapter = new OrderHistoryAdapter(this);
        orederHistoryListView.setOnItemClickListener(this);
        orederHistoryListView.setAdapter(orderHistoryAdapter);


        mInternetConnection = new InternetConnection(this);

        if (mInternetConnection.isConnectingToInternet()) {
            ordersArrayList.clear();
            new GetPreViousOrderProductAsyncTask(this).execute();


        } else {
            Intent intent = new Intent(this, NoInternetActiviy.class);
            startActivity(intent);
            finish();
        }


    }

    public void notifyDataSet() {
        orderHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,OrderHistoryDetailsPage.class);
        intent.putExtra("position", position);
        startActivity(intent);


    }
}
