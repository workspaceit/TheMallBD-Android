package com.workspaceit.themallbd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.WishListActivity;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

/**
 * Created by Mausum on 4/11/2016.
 */
public class WishInListViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private WishListActivity context;
    private static String productUrl = "product/thumbnail/";

    public WishInListViewAdapter(WishListActivity activity){
        context=activity;
        layoutInflater=context.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return Utility.wishlistProductArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return Utility.wishlistProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Utility.wishlistProductArrayList.get(position).id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.wishlist_item,null);
            viewHolder=new ViewHolder();

            viewHolder.wishListImage=(ImageView)convertView.findViewById(R.id.imageview_wishlist);
            viewHolder.productName=(TextView)convertView.findViewById(R.id.pname_wishlist_textview);
            viewHolder.productPrice=(TextView)convertView.findViewById(R.id.pprice_wishlist_textview);
            viewHolder.addButton=(Button)convertView.findViewById(R.id.addbutton_wishlist);

            viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context.addProductTotheCart(position)){
                        MakeToast.showToast(context,"Successfully added to the cart");

                    }else {

                    }

                }
            });



        }


        try{
            String icon=Utility.wishlistProductArrayList.get(position).pictures.get(0).name;
            int size = icon!=null? icon.length(): 0;
            if (size>0) {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +icon,
                        viewHolder.wishListImage);
            } else {
                viewHolder.wishListImage.setImageResource(R.drawable.image_not_found);
            }

            viewHolder.productName.setText(Utility.wishlistProductArrayList.get(position).title);
            viewHolder.productPrice.setText("$"+String.valueOf(Utility.wishlistProductArrayList.get(position).prices.get(0).retailPrice));


        }catch (Exception e){

        }

        return convertView;
    }



    public class ViewHolder {

        public ImageView wishListImage;
        public TextView productName;
        public TextView productPrice;
        public Button addButton;



    }
}
