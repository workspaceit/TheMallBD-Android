package com.themallbd.workspaceit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.themallbd.workspaceit.adapter.CategoryWiseProductsAdapter;
import com.themallbd.workspaceit.adapter.SearchByKeywordProductAdapter;
import com.themallbd.workspaceit.adapter.SearchProductAdapter;
import com.themallbd.workspaceit.asynctask.CategoryWiseProductsAsyncTask;
import com.themallbd.workspaceit.asynctask.GetSearchProductByKeywordAsynTask;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

import java.util.ArrayList;

public class SearchProductListActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {
    private ListView searchProductListView;

    private InternetConnection mInternetConnection;



    public static ArrayList<Products> searchProductArrayList;
    private String keyword;
    private int offset = 0,limit = 5;
    private SearchByKeywordProductAdapter searchByKeywordProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_list);

        keyword=getIntent().getStringExtra("keyword");
        MakeToast.showToast(this,keyword);

        initialize();
    }

    private void initialize() {

        mInternetConnection = new InternetConnection(this);
        searchProductArrayList=new ArrayList<>();
        searchProductListView = (ListView) findViewById(R.id.product_search_listView);
        searchByKeywordProductAdapter=new SearchByKeywordProductAdapter(this);
        searchProductListView.setAdapter(searchByKeywordProductAdapter);




        if (mInternetConnection.isConnectingToInternet())
        {

            new GetSearchProductByKeywordAsynTask(this).execute(keyword,String.valueOf(limit),String.valueOf(offset));


        }

        searchProductListView.setOnItemClickListener(this);

    }

    public void notifyDataSetChange(){
        searchByKeywordProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
