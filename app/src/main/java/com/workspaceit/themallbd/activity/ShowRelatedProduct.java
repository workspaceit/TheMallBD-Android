package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.RelatedProductAdapter;
import com.workspaceit.themallbd.asynctask.GetRelatedProductAsynTask;
import com.workspaceit.themallbd.utility.CustomListView;
import com.workspaceit.themallbd.utility.Utility;

public class ShowRelatedProduct extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private CustomListView relatedProductListView;
    private RelatedProductAdapter relatedProductAdapter;
    private TextView titleTextView;
    private ScrollView scrollView;
    private int productID;
    private int categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_related_product);

        titleTextView = (TextView) findViewById(R.id.show_related_product_title_text);
        titleTextView.setVisibility(View.GONE);
        productID = getIntent().getIntExtra("product_id", -1);
        categoryId = getIntent().getIntExtra("category_id", -1);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_show_related_product);


        relatedProductListView = (CustomListView) findViewById(R.id.related_product_page_list_view);
        relatedProductAdapter = new RelatedProductAdapter(this, 23);
        relatedProductListView.setAdapter(relatedProductAdapter);
        relatedProductListView.setOnItemClickListener(this);

        Utility.relatedProductArryList.clear();
        relatedProductAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetRelatedProductAsynTask(this, 2).execute(String.valueOf(6), String.valueOf(0), String.valueOf(productID),
                String.valueOf(categoryId));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("productArray", 6);
        startActivity(intent);
        this.finish();

    }

    public void setAdapterDataSet() {

        titleTextView.setVisibility(View.VISIBLE);
        relatedProductAdapter.notifyDataSetChanged();
        scrollView.smoothScrollTo(0, 0);

    }
}
