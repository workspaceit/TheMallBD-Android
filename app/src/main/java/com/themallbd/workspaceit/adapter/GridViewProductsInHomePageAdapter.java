package com.themallbd.workspaceit.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by rajib on 2/15/16.
 */
public class GridViewProductsInHomePageAdapter extends BaseAdapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG  = 0;
    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;
    private int state;

    private static String productUrl = "product/general/";


    public GridViewProductsInHomePageAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        this.layoutInflater = mainActivity.getLayoutInflater();
    }

    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;
        public TextView discointTextView;

    }

    @Override
    public int getCount() {
        return MainActivity.allProductsForGridViewList.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.allProductsForGridViewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        ViewHolder viewHolder = null;


        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_grid_view_item,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.iv_productImage_gv);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.tv_productName_gv);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.tv_productPrice_gv);
            viewHolder.discointTextView = (TextView) convertView.findViewById(R.id.tv_product_discount_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {


            int size = MainActivity.allProductsForGridViewList.get(position).pictures.size();
            if (size>=1) {

                ImageLoader imageLoader=ImageLoader.getInstance();

                imageLoader.displayImage(
                        Utility.IMAGE_URL + productUrl +
                                MainActivity.allProductsForGridViewList.get(position).pictures.get(0).name,
                        viewHolder.productImage);
            } else {
                viewHolder.productImage.setImageResource(R.drawable.image_not_found);
            }
            viewHolder.productName.setText(MainActivity.allProductsForGridViewList.get(position).title);

            viewHolder.discointTextView.setVisibility(View.VISIBLE);
            viewHolder.priceView.setPaintFlags(viewHolder.priceView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            if (MainActivity.allProductsForGridViewList.get(position).discountActiveFlag) {

                viewHolder.priceView.setText(MainActivity.allProductsForGridViewList.get(position).prices.get(0).retailPrice + " " + Utility.CURRENCY_CODE);
                viewHolder.priceView.setPaintFlags(viewHolder.priceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                float currentPrice = (float) (MainActivity.allProductsForGridViewList.get(position).prices.get(0).retailPrice - MainActivity.allProductsForGridViewList.get(position).discountAmount);

                viewHolder.discointTextView.setText(currentPrice + " "+Utility.CURRENCY_CODE);
            } else {
                viewHolder.discointTextView.setVisibility(View.GONE);

                viewHolder.priceView.setText("" + MainActivity.allProductsForGridViewList.get(position).prices.get(0).retailPrice+" "+Utility.CURRENCY_CODE);


            }





        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        state += 1;
        return convertView;
    }
}
