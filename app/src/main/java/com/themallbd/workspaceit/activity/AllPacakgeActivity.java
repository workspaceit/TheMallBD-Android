package com.themallbd.workspaceit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.AllPackageAdapter;
import com.themallbd.workspaceit.asynctask.GetPackagesAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

public class AllPacakgeActivity extends BaseActivityWithoutDrawer implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private ListView allPackageListView;
    private AllPackageAdapter allPackageAdapter;
    private int limit=5;
    private int offset=0;
    private InternetConnection mInternetConnection;
    private boolean loadProductFlag=true;
    private View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pacakge);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolBarTitle=(TextView)toolbar.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Packages");
        allPackageListView =(ListView)findViewById(R.id.all_package_list_view);
        allPackageAdapter=new AllPackageAdapter(this);
        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.page_list_view_loader, null, false);
        allPackageListView.addFooterView(footer);
        footer.setVisibility(View.GONE);

        allPackageListView.setAdapter(allPackageAdapter);

        mInternetConnection=new InternetConnection(this);
        this.offset=((MainActivity.packgeProductForHorizontalList.size()/5)-1);




        this.allPackageListView.setOnScrollListener(this);
        this.allPackageListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PackageDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("productArray", 1);
        startActivity(intent);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount>=totalItemCount && loadProductFlag && MainActivity.morePackage){


            loadProductFlag=false;
            if (mInternetConnection.checkInternet())
            {
                this.footer.setVisibility(View.VISIBLE);
                this.offset++;


                new GetPackagesAsynTask(this).execute(String.valueOf(this.limit), String.valueOf(this.offset));



            }else {
                Intent intent=new Intent(this,NoInternetActiviy.class);
                startActivity(intent);
                finish();
            }

        }

    }

    public void notifyDataSetChange() {
        this.footer.setVisibility(View.GONE);
        this.allPackageAdapter.notifyDataSetChanged();
        loadProductFlag=true;
    }

    public void newProductError(){
        this.allPackageListView.removeFooterView(footer);
        loadProductFlag=false;
        MainActivity.morePackage=false;
        MakeToast.showToast(this, "No More Package...");
    }
}
