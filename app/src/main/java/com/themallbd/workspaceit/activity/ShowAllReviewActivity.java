package com.themallbd.workspaceit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.themallbd.workspaceit.adapter.ReviewSingleRowAdapter;
import com.themallbd.workspaceit.asynctask.GetReviewAsynTask;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.utility.CustomListView;

public class ShowAllReviewActivity extends BaseActivityWithoutDrawer {

    private TextView titleText;
    private CustomListView allReviewListView;

    private int productId;
    private ReviewSingleRowAdapter reviewSingleRowAdapter;
    int limit;
    int offset=0;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_review);

        titleText=(TextView)findViewById(R.id.show_review_title_text_view);
        titleText.setVisibility(View.GONE);

        allReviewListView=(CustomListView)findViewById(R.id.review_all_list_view);
        productId = getIntent().getIntExtra("product_id", -1);
        limit= getIntent().getIntExtra("review_count", -1);

        reviewSingleRowAdapter=new ReviewSingleRowAdapter(this);
        allReviewListView.setAdapter(reviewSingleRowAdapter);
        scrollView=(ScrollView)findViewById(R.id.scroll_view_show_review);
        Utility.reviews.clear();
        reviewSingleRowAdapter.notifyDataSetChanged();

        new GetReviewAsynTask(this,2).execute(String.valueOf(productId), String.valueOf(limit),
                String.valueOf(offset));


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    public void setReviewDataSet() {

        titleText.setVisibility(View.VISIBLE);
        reviewSingleRowAdapter.notifyDataSetChanged();
        scrollView.smoothScrollTo(0, 0);

    }

}
