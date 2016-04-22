package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.RelatedProductAdapter;
import com.workspaceit.themallbd.utility.RelatedProductListView;

public class ShowRelatedProduct extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private RelatedProductListView relatedProductListView;
    private RelatedProductAdapter relatedProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_related_product);

        relatedProductListView=(RelatedProductListView)findViewById(R.id.related_product_page_list_view);
        relatedProductAdapter=new RelatedProductAdapter(this,23);
        relatedProductListView.setAdapter(relatedProductAdapter);
        relatedProductListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",6);
        startActivity(intent);

    }
}
