package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.themallbd.workspaceit.adapter.AllManufracturerAdapter;
import com.themallbd.workspaceit.asynctask.GetManufracturerAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class ManufracturerActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {
    private GridView manufracturerGridView;
    private InternetConnection internetConnection;
    private AllManufracturerAdapter allManufracturerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufracturer);

        manufracturerGridView = (GridView) findViewById(R.id.manufracturer_grid_view);
        manufracturerGridView.setOnItemClickListener(this);
        allManufracturerAdapter=new AllManufracturerAdapter(this);
        internetConnection = new InternetConnection(this);
        if (internetConnection.checkInternet()) {
            if (Utility.manufacturers.size() > 0) {
                notifyDataSetForManufracturer();
            } else {
                new GetManufracturerAsynTask(this).execute();
            }
        } else {

            Intent intent = new Intent(this, NoInternetActiviy.class);
            startActivity(intent);
            finish();

        }


    }

    public void notifyDataSetForManufracturer(){
        manufracturerGridView.setAdapter(allManufracturerAdapter);
        allManufracturerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent==manufracturerGridView){
            Intent intent=new Intent(this,ProductByManufracturerActivity.class);
            intent.putExtra("manufracturer_id",Utility.manufacturers.get(position).id);
            startActivity(intent);
        }

    }
}
