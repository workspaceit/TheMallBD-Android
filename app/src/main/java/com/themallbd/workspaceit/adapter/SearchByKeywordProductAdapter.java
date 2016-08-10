package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.SearchProductListActivity;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 7/19/2016.
 */
public class SearchByKeywordProductAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private String productUrl = "product/general/";

    public SearchByKeywordProductAdapter(Activity activity){
        this.activity=activity;
        this.layoutInflater =activity.getLayoutInflater();


    }

    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;
        public TextView manufraturer;
        public TextView discountPrice;
    }

    @Override
    public int getCount() {
        return SearchProductListActivity.searchProductArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return SearchProductListActivity.searchProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.any_products_list_item,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_category_imageview);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_category_name);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.product_category_price);
            viewHolder.manufraturer=(TextView)convertView.findViewById(R.id.any_product_manufracturer_text_view);
            viewHolder.discountPrice=(TextView)convertView.findViewById(R.id.product_discount_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            int size = SearchProductListActivity.searchProductArrayList.get(position).pictures.size();
            if (size>=1) {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +
                                SearchProductListActivity.searchProductArrayList.get(position).pictures.get(0).name,
                        viewHolder.productImage);
            } else {


                BitmapFactory.Options ourOptions=new BitmapFactory.Options();
                ourOptions.inDither=false;
                ourOptions.inPurgeable=true;
                ourOptions.inInputShareable=true;
                ourOptions.inTempStorage=new byte[32 * 1024];
                Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.image_not_found, ourOptions);
                // viewHolder.productImage.setImageResource(R.drawable.image_not_found);
                viewHolder.productImage.setImageBitmap(bm);
            }
            viewHolder.productName.setText(SearchProductListActivity.searchProductArrayList.get(position).title);
            viewHolder.manufraturer.setText(SearchProductListActivity.searchProductArrayList.get(position).manufacturer.name);


            viewHolder.discountPrice.setVisibility(View.VISIBLE);
            viewHolder.priceView.setPaintFlags(viewHolder.priceView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            if (SearchProductListActivity.searchProductArrayList.get(position).discountActiveFlag) {

                viewHolder.priceView.setText(SearchProductListActivity.searchProductArrayList.get(position).prices.get(0).retailPrice + " " + Utility.CURRENCY_CODE);
                viewHolder.priceView.setPaintFlags(viewHolder.priceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                float currentPrice = (float) (SearchProductListActivity.searchProductArrayList.get(position).prices.get(0).retailPrice - SearchProductListActivity.searchProductArrayList.get(position).discountAmount);

                viewHolder.discountPrice.setText(currentPrice + " "+Utility.CURRENCY_CODE);
            } else {
                viewHolder.discountPrice.setVisibility(View.GONE);


                viewHolder.priceView.setText(SearchProductListActivity.searchProductArrayList.get(position).prices.get(0).retailPrice+" "+Utility.CURRENCY_CODE);
            }



        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }
}
