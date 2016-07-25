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
public class PackageInHorizontalListAdapter extends RecyclerView.Adapter<PackageInHorizontalListAdapter.ViewHolder> {
    private String packageUrl = "package/general/";
    private MainActivity mainActivity;

    public PackageInHorizontalListAdapter(MainActivity mainActivity) {
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
        viewHolder.nameTextView.setText(MainActivity.packgeProductForHorizontalList.get(position).packageTitle);

            viewHolder.priceTextView.setText("" + MainActivity.packgeProductForHorizontalList.get(position).originalPriceTotal);



            ImageLoader imageLoader = ImageLoader.getInstance();

            imageLoader.getInstance().displayImage(
                    Utility.IMAGE_URL + packageUrl + MainActivity.packgeProductForHorizontalList.get(position).image,
                    viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return MainActivity.packgeProductForHorizontalList.size();
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
