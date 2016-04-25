package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.RelatedProductAdapter;
import com.workspaceit.themallbd.asynctask.GetRelatedProductAsynTask;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.RelatedProductListView;
import com.workspaceit.themallbd.utility.Utility;

public class ShowRelatedProduct extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private RelatedProductListView relatedProductListView;
    private RelatedProductAdapter relatedProductAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_related_product);

        int productID=getIntent().getIntExtra("product_id", -1);
        int categoryId=getIntent().getIntExtra("category_id", -1);


        relatedProductListView=(RelatedProductListView)findViewById(R.id.related_product_page_list_view);
        relatedProductAdapter=new RelatedProductAdapter(this,23);
        relatedProductListView.setAdapter(relatedProductAdapter);
        relatedProductListView.setOnItemClickListener(this);

        Utility.relatedProductArryList.clear();
        relatedProductAdapter.notifyDataSetChanged();
        new GetRelatedProductAsynTask(this,2).execute(String.valueOf(6), String.valueOf(0), String.valueOf(productID),
                String.valueOf(categoryId));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("productArray", 6);
        startActivity(intent);

    }

    public void setAdapterDataSet(){
        relatedProductAdapter.notifyDataSetChanged();
    }
}
