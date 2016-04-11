package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.WishInListViewAdapter;
import com.workspaceit.themallbd.asynctask.GetNewProductsAsyncTask;
import com.workspaceit.themallbd.asynctask.GetWishListProductAsynTask;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

public class WishListActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    ListView wishListListView;
    WishInListViewAdapter wishInListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Wishlist");


        wishListListView=(ListView)findViewById(R.id.wish_list_listview);
        wishInListViewAdapter=new WishInListViewAdapter(this);
        wishListListView.setAdapter(wishInListViewAdapter);
        wishListListView.setOnItemClickListener(this);
        new GetWishListProductAsynTask(this).execute();
    }


    public void changeAdapterState(){


            wishInListViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",5);
        startActivity(intent);

    }
}
