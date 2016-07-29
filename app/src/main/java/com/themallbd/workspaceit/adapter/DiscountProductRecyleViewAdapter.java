package com.themallbd.workspaceit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 7/25/2016.
 */
public class DiscountProductRecyleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG  = 0;
    private String productUrl = "product/general/";
    private MainActivity mainActivity;

    public DiscountProductRecyleViewAdapter(MainActivity mainActivity){
        this.mainActivity=mainActivity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
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

            vh = new HorizontalRecyclerViewAdapter.ProgressViewHolder(v);

        }


        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return MainActivity.discountProductForHorizontalList.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ViewHolder) {
            DiscountProductRecyleViewAdapter.ViewHolder viewHolder = (DiscountProductRecyleViewAdapter.ViewHolder) holder;

            // Set item views based on the data model
            viewHolder.nameTextView.setText(MainActivity.discountProductForHorizontalList.get(position).title);
            if (MainActivity.discountProductForHorizontalList.get(position).prices.size() > 0)
                viewHolder.priceTextView.setText("" + MainActivity.discountProductForHorizontalList.get(position).prices.get(0).retailPrice);
            else
                viewHolder.priceTextView.setText("no prices");

            int size = MainActivity.discountProductForHorizontalList.get(position).pictures.size();
            if (size >= 1) {
                ImageLoader imageLoader = ImageLoader.getInstance();

                imageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl + MainActivity.discountProductForHorizontalList.get(position).pictures.get(0).name,
                        viewHolder.imageView);

            } else {
                viewHolder.imageView.setImageResource(R.drawable.image_not_found);
            }
        }else {
            ((HorizontalRecyclerViewAdapter.ProgressViewHolder)holder).progressBar.setIndeterminate(true);
            ((HorizontalRecyclerViewAdapter.ProgressViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return MainActivity.discountProductForHorizontalList.size();
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
