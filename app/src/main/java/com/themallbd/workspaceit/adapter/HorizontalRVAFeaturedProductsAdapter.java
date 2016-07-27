package com.themallbd.workspaceit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.MainActivity;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.utility.Utility;

import java.util.List;

/**
 * Created by rajib on 2/15/16.
 */
public class HorizontalRVAFeaturedProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG  = 0;
    private List<Products> productsList;

    private static String productUrl = "product/general/";
    private MainActivity mainActivity;

    // Pass in the contact array into the constructor
    public HorizontalRVAFeaturedProductsAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        RecyclerView.ViewHolder vh;
        if(viewType==VIEW_ITEM){
            // Inflate the custom layout
            View productView = inflater.inflate(R.layout.custom_horizontal_product_list, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(productView);
            vh=viewHolder;

        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_view_loader, parent, false);

            vh = new ProgressViewHolder(v);

        }


        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ViewHolder) {
            HorizontalRVAFeaturedProductsAdapter.ViewHolder viewHolder=(HorizontalRVAFeaturedProductsAdapter.ViewHolder)holder;
            // Set item views based on the data model
            viewHolder.nameTextView.setText(MainActivity.featuredProductsForHorizontalViewList.get(position).title);
            if (MainActivity.featuredProductsForHorizontalViewList.get(position).prices.size() > 0)
                viewHolder.priceTextView.setText("" + MainActivity.featuredProductsForHorizontalViewList.get(position).prices.get(0).retailPrice);
            else
                viewHolder.priceTextView.setText("no prices");
            int size = MainActivity.featuredProductsForHorizontalViewList.get(position).pictures.size();
            if (size >= 1) {
                ImageLoader imageLoader = ImageLoader.getInstance();

                imageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl + MainActivity.featuredProductsForHorizontalViewList.get(position).pictures.get(0).name,
                        viewHolder.imageView);
            } else {
                viewHolder.imageView.setImageResource(R.drawable.image_not_found);
            }
        }else {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
            ((ProgressViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return MainActivity.featuredProductsForHorizontalViewList.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return MainActivity.featuredProductsForHorizontalViewList.size();
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


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        }
    }
}
