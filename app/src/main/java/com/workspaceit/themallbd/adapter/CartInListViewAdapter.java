package com.workspaceit.themallbd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.dataModel.ShoppingCart;
import com.workspaceit.themallbd.fragment.CartFragment;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 3/22/16.
 */
public class CartInListViewAdapter extends BaseAdapter {

    private static String productUrl = "product/thumbnail/";

    private CartFragment mCartFragment;
    private ShoppingCart shoppingCart;
    private LayoutInflater layoutInflater;
    private Activity context;

    public CartInListViewAdapter(Activity cartFragment, ShoppingCart shop) {
        this.context = cartFragment;
       // this.mCartFragment = cartFragment;
        this.shoppingCart = shop;
        layoutInflater = context.getLayoutInflater();

    }

    public class ViewHolder {

        public ImageView cartItemImage;
        public TextView cartItemName;
        public TextView cartItemPrice;
        public TextView quantityTextView;
        public Button cartItemAdd;
        public Button cartItemMinus;
        public Button cartItemDelete;


    }

    @Override
    public int getCount() {
        return this.shoppingCart.shoppingCartCell.size();
    }

    @Override
    public Object getItem(int position) {
        return this.shoppingCart.shoppingCartCell.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.shoppingCart.shoppingCartCell.get(position).id;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            //layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_item,null);
            viewHolder = new ViewHolder();

            viewHolder.cartItemImage = (ImageView) convertView.findViewById(R.id.cart_item_imageView);
            viewHolder.cartItemName = (TextView) convertView.findViewById(R.id.cart_item_name_tv);
            viewHolder.cartItemPrice = (TextView) convertView.findViewById(R.id.cart_item_price_tv);
            viewHolder.cartItemAdd=(Button)convertView.findViewById(R.id.addButton);
            viewHolder.cartItemDelete=(Button)convertView.findViewById(R.id.cart_delete_btn);
            viewHolder.cartItemMinus=(Button)convertView.findViewById(R.id.minusButton);
            viewHolder.quantityTextView=(TextView)convertView.findViewById(R.id.quantityTextView);

            viewHolder.cartItemAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.shoppingCart.shoppingCartCell.get(position).quantity+=1;
                    CartInListViewAdapter.this.notifyDataSetChanged();
                }
            });

            viewHolder.cartItemMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity=Utility.shoppingCart.shoppingCartCell.get(position).quantity;
                    quantity--;

                    if(quantity<1){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("are u sure?");
                        alertDialogBuilder
                                .setMessage("If you select 0 quantity, the product will be removed from the cart.")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Utility.shoppingCart.shoppingCartCell.remove(position);
                                        CartInListViewAdapter.this.notifyDataSetChanged();

                                    }
                                }).
                                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();


                        alertDialog.show();
                    }else {

                        Utility.shoppingCart.shoppingCartCell.get(position).quantity -= 1;
                    }
                    CartInListViewAdapter.this.notifyDataSetChanged();




                }
            });

            viewHolder.cartItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.shoppingCart.shoppingCartCell.remove(position);
                    CartInListViewAdapter.this.notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            String icon = this.shoppingCart.shoppingCartCell.get(position).product.pictures.get(0).name;
            int size = icon!=null? icon.length(): 0;
            if (size>0) {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +icon,
                        viewHolder.cartItemImage);
            } else {
                viewHolder.cartItemImage.setImageResource(R.drawable.image_not_found);
            }

            viewHolder.cartItemName.setText(this.shoppingCart.shoppingCartCell.get(position).product.title);
            viewHolder.cartItemPrice.setText("" + this.shoppingCart.shoppingCartCell.get(position).product.prices.get(0).retailPrice);

            viewHolder.quantityTextView.setText(String.valueOf(this.shoppingCart.shoppingCartCell.get(position).quantity));
        }
        catch (Exception e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }
}
