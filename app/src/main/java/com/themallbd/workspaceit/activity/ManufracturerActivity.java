package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.themallbd.workspaceit.adapter.AllManufracturerAdapter;
import com.themallbd.workspaceit.asynctask.GetManufracturerAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class ManufracturerActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {
    private GridView manufracturerGridView;
    private InternetConnection internetConnection;
    private AllManufracturerAdapter allManufracturerAdapter;
    private int limit=6;
    private int offset=0;
    private boolean loadProductFlag=true;
    private static boolean moreManufracturer=true;
    private InternetConnection mInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufracturer);
        mInternetConnection=new InternetConnection(this);
        manufracturerGridView = (GridView) findViewById(R.id.manufracturer_grid_view);
        manufracturerGridView.setOnItemClickListener(this);

        allManufracturerAdapter=new AllManufracturerAdapter(this);
        manufracturerGridView.setAdapter(allManufracturerAdapter);
        internetConnection = new InternetConnection(this);
        if (internetConnection.checkInternet()) {
            if (Utility.manufacturers.size() > 0) {
                this.offset=(Utility.manufacturers.size()/6)-1;
                notifyDataSetForManufracturer();
            } else {
                loadProductFlag=false;
                new GetManufracturerAsynTask(this).execute(String.valueOf(limit),String.valueOf(offset));
            }
        } else {

            Intent intent = new Intent(this, NoInternetActiviy.class);
            startActivity(intent);
            finish();

        }
        manufracturerGridView.setOnScrollListener(this);

    }

    public void notifyDataSetForManufracturer(){

        allManufracturerAdapter.notifyDataSetChanged();
        loadProductFlag=true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent==manufracturerGridView){
            Intent intent=new Intent(this,ProductByManufracturerActivity.class);
            intent.putExtra("manufracturer_id",Utility.manufacturers.get(position).id);
            startActivity(intent);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount>=totalItemCount && loadProductFlag && moreManufracturer){


            loadProductFlag=false;
            if (mInternetConnection.checkInternet())
            {


                this.offset++;


                new GetManufracturerAsynTask(this).execute(String.valueOf(this.limit), String.valueOf(this.offset));



            }else {
                Intent intent=new Intent(this,NoInternetActiviy.class);
                startActivity(intent);
                finish();
            }

        }
    }


    public void manufracturerError(){
        moreManufracturer=false;
    }
}
