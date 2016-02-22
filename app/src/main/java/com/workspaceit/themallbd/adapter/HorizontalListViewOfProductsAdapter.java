package com.workspaceit.themallbd.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.Utility;

/**
 * Created by rajib on 2/15/16.
 */
public class HorizontalListViewOfProductsAdapter extends BaseAdapter{

    private static String productUrl = "/product/large/";

    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;
    private int state;
    private int screenWidth;

    DisplayImageOptions displayImageOptions;
    public HorizontalListViewOfProductsAdapter(MainActivity mainActivity,int width) {
        this.mainActivity = mainActivity;
        screenWidth = width;
        this.layoutInflater = mainActivity.getLayoutInflater();

        displayImageOptions =  new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .build();


    }

    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;

    }

    @Override
    public int getCount() {
        return MainActivity.newProductsForHorizontalViewList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_horizontal_product_list,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.iv_productImage_hl);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.tv_productName_hl);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.tv_productPrice_hl);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            if (MainActivity.newProductsForHorizontalViewList.get(position).pictures.get(0).name != null) {
                ImageLoader.getInstance().displayImage(Utility.IMAGE_URL + productUrl+MainActivity.newProductsForHorizontalViewList.get(position).pictures.get(0).name, viewHolder.productImage);
            } else {
                viewHolder.productImage.setImageResource(R.drawable.cart);
            }
            viewHolder.productName.setText(MainActivity.newProductsForHorizontalViewList.get(position).title);
            if (MainActivity.newProductsForHorizontalViewList.get(position).prices.size()>0)
                viewHolder.priceView.setText(""+MainActivity.newProductsForHorizontalViewList.get(position).prices.get(0).retailPrice);
            else
                viewHolder.priceView.setText("No price");
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
