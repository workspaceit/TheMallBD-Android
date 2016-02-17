package com.workspaceit.themallbd.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.dataModel.Products;

import java.util.List;

/**
 * Created by rajib on 2/15/16.
 */
public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder> {

    private List<Products> productsList;

    private static  String IMAGE_URL = "http://192.168.1.19/mallbdweb/public/product_images/";
    private MainActivity mainActivity;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    // Pass in the contact array into the constructor
    public HorizontalRecyclerViewAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View productView = inflater.inflate(R.layout.custom_horizontal_product_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Set item views based on the data model
        viewHolder.nameTextView.setText(MainActivity.newProductsForHorizontalViewList.get(position).title);
        if (MainActivity.newProductsForHorizontalViewList.get(position).prices.size()>0)
            viewHolder.priceTextView.setText("" + MainActivity.newProductsForHorizontalViewList.get(position).prices.get(0).retailPrice);
        else
            viewHolder.priceTextView.setText("no prices");
        if (MainActivity.newProductsForHorizontalViewList.get(position).pictures.get(0).name != null) {
            imageLoader.displayImage(IMAGE_URL+MainActivity.newProductsForHorizontalViewList.get(position).pictures.get(0).name, viewHolder.imageView);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.cart);
        }

    }
    @Override
    public int getItemCount() {
        return MainActivity.newProductsForHorizontalViewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageView;
        public TextView nameTextView;
        public TextView priceTextView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.iv_productImage_hl);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_productName_hl);
            priceTextView = (TextView) itemView.findViewById(R.id.tv_productPrice_hl);
        }
    }
}
