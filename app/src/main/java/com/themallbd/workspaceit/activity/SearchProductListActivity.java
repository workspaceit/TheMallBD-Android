package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.SearchByKeywordProductAdapter;
import com.themallbd.workspaceit.asynctask.GetSearchProductByKeywordAsynTask;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

import java.util.ArrayList;

public class SearchProductListActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {
    private ListView searchProductListView;

    private InternetConnection mInternetConnection;
    private boolean loadSerachFlag=true;
    private boolean moreProduct =true;
    private TextView searchResultTextView;
    private TextView noSearchResult;




    public static ArrayList<Products> searchProductArrayList;
    private String keyword;
    private int offset = 0,limit = 5;
    private SearchByKeywordProductAdapter searchByKeywordProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_list);

        keyword=getIntent().getStringExtra("keyword");


        initialize();
    }

    private void initialize() {
        offset=0;

        searchResultTextView=(TextView)findViewById(R.id.search_result_text_view);
        searchResultTextView.setText("Search Result for '"+keyword+"'");
        noSearchResult=(TextView)findViewById(R.id.no_search_result_text_view);

        mInternetConnection = new InternetConnection(this);
        searchProductArrayList=new ArrayList<>();
        searchProductListView = (ListView) findViewById(R.id.product_search_listView);
        searchByKeywordProductAdapter=new SearchByKeywordProductAdapter(this);
        searchProductListView.setAdapter(searchByKeywordProductAdapter);


        searchProductListView.setOnScrollListener(this);

        searchProductListView.setOnItemClickListener(this);

    }

    public void NoSearcResult(){
        noSearchResult.setVisibility(View.VISIBLE);
    }
    public void notifyDataSetChange(){
        loadSerachFlag=true;
        searchByKeywordProductAdapter.notifyDataSetChanged();
        offset++;
    }

    public void NoMoreProductInSearch(){
        moreProduct =false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",8);
        startActivity(intent);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(firstVisibleItem+visibleItemCount>=totalItemCount && loadSerachFlag && moreProduct){


            loadSerachFlag=false;
            if (mInternetConnection.checkInternet())
            {


                new GetSearchProductByKeywordAsynTask(this).execute(keyword,String.valueOf(limit),String.valueOf(offset));


            }else {
              Intent intent=new Intent(this,NoInternetActiviy.class);
                startActivity(intent);
            }

        }





    }




}
