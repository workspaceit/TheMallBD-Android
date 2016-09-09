package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.themallbd.workspaceit.adapter.AnyProductListAdapter;
import com.themallbd.workspaceit.asynctask.GetProductByManufracturerIdAsynTask;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

import java.util.ArrayList;

public class ProductByManufracturerActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private int manufracturerId=0;
    private AnyProductListAdapter anyProductListAdapter;
    public static ArrayList<Products> productListByMenufracturer=new ArrayList<>();
    private InternetConnection internetConnection;
    private GridView productByManufracturerGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_manufracturer);

        productByManufracturerGridView=(GridView)findViewById(R.id.product_by_manufracturer_grid_view);
        productByManufracturerGridView.setOnItemClickListener(this);
        manufracturerId=getIntent().getIntExtra("manufracturer_id",0);


        internetConnection=new InternetConnection(this);
        if (internetConnection.checkInternet()){
            productListByMenufracturer.clear();
            new GetProductByManufracturerIdAsynTask(this).execute(String.valueOf(manufracturerId));
        }else {
            Intent intent=new Intent(this,NoInternetActiviy.class);
            startActivity(intent);
            finish();
        }


    }

    public void MakeAdapterWork(){


        anyProductListAdapter=new AnyProductListAdapter(this,productListByMenufracturer);
        productByManufracturerGridView.setAdapter(anyProductListAdapter);
        anyProductListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent==productByManufracturerGridView){
            Intent intent = new Intent(this,ProductDetailsActivity.class);
            intent.putExtra("position",position);
            intent.putExtra("productArray",11);
            startActivity(intent);
        }
    }
}
