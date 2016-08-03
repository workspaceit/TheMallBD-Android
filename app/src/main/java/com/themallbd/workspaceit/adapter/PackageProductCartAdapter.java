package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.dataModel.MallBdPackageCell;
import com.themallbd.workspaceit.dataModel.ShoppingCart;
import com.themallbd.workspaceit.fragment.CartFragment;
import com.themallbd.workspaceit.utility.LocalShoppintCart;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/26/2016.
 */
public class PackageProductCartAdapter extends BaseAdapter {
    private String packageUrl = "package/general/";

    private CartFragment mCartFragment;
    private ShoppingCart shoppingCart;
    private LayoutInflater layoutInflater;
    private Activity context;
    Gson gson;
    LocalShoppintCart localShoppintCart;

    public PackageProductCartAdapter(Activity cartFragment, ShoppingCart shop){
        this.context = cartFragment;
        // this.mCartFragment = cartFragment;
        this.shoppingCart = shop;
        layoutInflater = context.getLayoutInflater();
        gson=new Gson();
        localShoppintCart=new LocalShoppintCart(this.context);

    }

    @Override
    public int getCount() {
        return shoppingCart.mallBdPackageCell.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCart.mallBdPackageCell.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shoppingCart.mallBdPackageCell.get(position).id;
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
            viewHolder.singleSubTotal=(TextView)convertView.findViewById(R.id.single_sub_total_text_view);

            viewHolder.cartItemAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    

                    Utility.shoppingCart.mallBdPackageCell.get(position).quantity+=1;
                    PackageProductCartAdapter.this.notifyDataSetChanged();
                    String cart=gson.toJson(Utility.shoppingCart.mallBdPackageCell);
                    localShoppintCart=new LocalShoppintCart(context);
                    localShoppintCart.setPackageCart(cart);

                }
            });

            viewHolder.cartItemMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity=Utility.shoppingCart.mallBdPackageCell.get(position).quantity;
                    quantity--;

                    if(quantity<1){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("are u sure?");
                        alertDialogBuilder
                                .setMessage("If you select 0 quantity, this package will be removed from the cart.")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Utility.shoppingCart.mallBdPackageCell.remove(position);
                                        PackageProductCartAdapter.this.notifyDataSetChanged();

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

                        Utility.shoppingCart.mallBdPackageCell.get(position).quantity -= 1;
                    }

                    String cart=gson.toJson(Utility.shoppingCart.mallBdPackageCell);
                    localShoppintCart=new LocalShoppintCart(context);
                    localShoppintCart.setProductCart(cart);
                    PackageProductCartAdapter.this.notifyDataSetChanged();




                }
            });

            viewHolder.cartItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.shoppingCart.mallBdPackageCell.remove(position);

                    String cart=gson.toJson(Utility.shoppingCart.mallBdPackageCell);
                    localShoppintCart=new LocalShoppintCart(context);
                    localShoppintCart.setPackageCart(cart);
                    PackageProductCartAdapter.this.notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            String icon = this.shoppingCart.mallBdPackageCell.get(position).mallBdPackage.image;
            int size = icon!=null? icon.length(): 0;
            if (size>0) {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + packageUrl +icon,
                        viewHolder.cartItemImage);
            } else {
                viewHolder.cartItemImage.setImageResource(R.drawable.image_not_found);
            }

            viewHolder.cartItemName.setText(this.shoppingCart.mallBdPackageCell.get(position).mallBdPackage.packageTitle);
            viewHolder.cartItemPrice.setText("" + this.shoppingCart.mallBdPackageCell.get(position).mallBdPackage.packagePriceTotal);

            int quantity=this.shoppingCart.mallBdPackageCell.get(position).quantity;
            double price= this.shoppingCart.mallBdPackageCell.get(position).mallBdPackage.packagePriceTotal;
            double subTotal=quantity*price;
            viewHolder.singleSubTotal.setText(subTotal+" BDT");

            viewHolder.quantityTextView.setText(String.valueOf(this.shoppingCart.mallBdPackageCell.get(position).quantity));
        }
        catch (Exception e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }


    public class ViewHolder {

        public ImageView cartItemImage;
        public TextView cartItemName;
        public TextView cartItemPrice;
        public TextView quantityTextView;
        public Button cartItemAdd;
        public Button cartItemMinus;
        public Button cartItemDelete;
        public TextView singleSubTotal;


    }
}
