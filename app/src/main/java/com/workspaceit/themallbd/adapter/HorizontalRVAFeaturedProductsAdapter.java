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
import com.workspaceit.themallbd.utility.Utility;

import java.util.List;

/**
 * Created by rajib on 2/15/16.
 */
public class HorizontalRVAFeaturedProductsAdapter extends RecyclerView.Adapter<HorizontalRVAFeaturedProductsAdapter.ViewHolder>  {

    private List<Products> productsList;

    private static String productUrl = "product/large/";
    private MainActivity mainActivity;

    // Pass in the contact array into the constructor
    public HorizontalRVAFeaturedProductsAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;


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
        int size = MainActivity.newProductsForHorizontalViewList.get(position).pictures.size();
        if (size>=1) {
           // ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
          //  ImageLoader.getInstance().init(config);
            ImageLoader.getInstance().displayImage(
                    Utility.IMAGE_URL + productUrl + MainActivity.newProductsForHorizontalViewList.get(position).pictures.get(0).name,
                    viewHolder.imageView);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.image_not_found);
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
