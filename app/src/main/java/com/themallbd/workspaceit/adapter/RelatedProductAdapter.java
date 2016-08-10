package com.themallbd.workspaceit.adapter;

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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.ProductDetailsActivity;
import com.themallbd.workspaceit.activity.ShowRelatedProduct;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by Mausum on 4/22/2016.
 */
public class RelatedProductAdapter extends BaseAdapter {

    private Activity contex;
    private LayoutInflater layoutInflater;
    private static String productUrl = "product/thumbnail/";
    private int flag;

    public RelatedProductAdapter(Activity activity, int flag) {

        if(flag==1){
            this.contex=(ProductDetailsActivity)activity;
        }else {
            this.contex=(ShowRelatedProduct)activity;
        }
        this.contex = activity;
        layoutInflater = contex.getLayoutInflater();
        this.flag=flag;
    }

    @Override
    public int getCount() {
        return  Utility.relatedProductArryList.size();

    }

    @Override
    public Object getItem(int position) {
        return Utility.relatedProductArryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Utility.relatedProductArryList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.related_product_single_row, null);
            viewHolder = new ViewHolder();

            viewHolder.relatedProductImage = (ImageView) convertView.findViewById(R.id.image_related_product);
            viewHolder.priceTextView = (TextView) convertView.findViewById(R.id.price_related_product);
            viewHolder.ratingRelatedProduct = (RatingBar) convertView.findViewById(R.id.rating_bar_related_product);
            viewHolder.reviewsTextView = (TextView) convertView.findViewById(R.id.review_count_related_product);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_name_related_product);
            LayerDrawable stars = (LayerDrawable) viewHolder.ratingRelatedProduct.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor("#961C1E"), PorterDuff.Mode.SRC_ATOP);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        try {
            String icon = Utility.relatedProductArryList.get(position).pictures.get(0).name;
            int size = icon != null ? icon.length() : 0;
            if (size > 0) {

                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl + icon,
                        viewHolder.relatedProductImage);
            } else {
                viewHolder.relatedProductImage.setImageResource(R.drawable.image_not_found);
            }

            viewHolder.productName.setText(Utility.relatedProductArryList.get(position).title);
            viewHolder.priceTextView.setText(String.valueOf(Utility.relatedProductArryList.get(position).prices.get(0).retailPrice+" BDT"));
            viewHolder.ratingRelatedProduct.setRating(Utility.relatedProductArryList.get(position).avgRating);
            viewHolder.reviewsTextView.setText(Utility.relatedProductArryList.get(position).manufacturer.name);

        } catch (Exception e) {

        }


        return convertView;
    }


    public class ViewHolder {

        public ImageView relatedProductImage;
        public RatingBar ratingRelatedProduct;
        public TextView reviewsTextView;
        public TextView priceTextView;
        public TextView productName;


    }
}
