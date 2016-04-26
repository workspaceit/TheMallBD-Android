package com.workspaceit.themallbd.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.dataModel.Review;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/26/2016.
 */
public class ReviewSingleRowAdapter extends BaseAdapter {
    private Activity contex;
    private LayoutInflater layoutInflater;


    public ReviewSingleRowAdapter(Activity activity){
        this.contex=activity;
        this.layoutInflater=contex.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return Utility.reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return Utility.reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Utility.reviews.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.review_single_row, null);
            viewHolder = new ViewHolder();



            viewHolder.reviewRating = (RatingBar) convertView.findViewById(R.id.rating_review);
            LayerDrawable stars = (LayerDrawable) viewHolder.reviewRating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor("#961C1E"), PorterDuff.Mode.SRC_ATOP);
            viewHolder.noteReviewTextView=(TextView)convertView.findViewById(R.id.note_review_text_view);
            viewHolder.userNameTextView=(TextView)convertView.findViewById(R.id.review_user_name_text_view);
            viewHolder.dateTextView=(TextView)convertView.findViewById(R.id.review_date_text_view);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.reviewRating.setRating(Utility.reviews.get(position).rating);
        viewHolder.userNameTextView.setText(Utility.reviews.get(position).customer.firstName + " " + Utility.reviews.get(position).customer.lastName);
        viewHolder.noteReviewTextView.setText(Utility.reviews.get(position).note);
        viewHolder.dateTextView.setText(Utility.reviews.get(position).createdOn);

        return convertView;

    }


    public class ViewHolder {


        public RatingBar reviewRating;
        public TextView noteReviewTextView;
        public TextView userNameTextView;
        public TextView dateTextView;


    }
}
