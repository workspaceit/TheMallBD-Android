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

/**
 * Created by rajib on 2/15/16.
 */
public class GridViewProductsInHomePageAdapter extends BaseAdapter {
    //private static  String IMAGE_URL = "http://cabguardpro.com/";
    private static  String IMAGE_URL = "http://192.168.1.19/mallbdweb/public/product_images/";
    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;
    private int state;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    public GridViewProductsInHomePageAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        this.layoutInflater = mainActivity.getLayoutInflater();

        displayImageOptions =  new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mainActivity)
                .defaultDisplayImageOptions(displayImageOptions)
                .threadPriority(Thread.MAX_PRIORITY)
                .threadPoolSize(5)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);


    }

    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;

    }

    @Override
    public int getCount() {
        return MainActivity.allProductsForGridViewList.size();
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
            convertView = layoutInflater.inflate(R.layout.custom_grid_view_item,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.iv_productImage_gv);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.tv_productName_gv);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.tv_productPrice_gv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            if (MainActivity.allProductsForGridViewList.get(position).pictures.get(0).name != null) {
                imageLoader.displayImage(IMAGE_URL+MainActivity.allProductsForGridViewList.get(position).pictures.get(0).name, viewHolder.productImage);
            } else {
                viewHolder.productImage.setImageResource(R.drawable.cart);
            }
            viewHolder.productName.setText(MainActivity.allProductsForGridViewList.get(position).title);
            if (MainActivity.allProductsForGridViewList.get(position).prices.size()>0)
                viewHolder.priceView.setText(""+MainActivity.allProductsForGridViewList.get(position).prices.get(0).retailPrice);
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
