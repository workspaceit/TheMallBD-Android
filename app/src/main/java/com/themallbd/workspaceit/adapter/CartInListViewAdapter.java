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
import com.themallbd.workspaceit.dataModel.ShoppingCart;
import com.themallbd.workspaceit.fragment.CartFragment;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by rajib on 3/22/16.
 */
public class CartInListViewAdapter extends BaseAdapter {

    private static String productUrl = "product/general/";

    private CartFragment mCartFragment;
    private ShoppingCart shoppingCart;
    private LayoutInflater layoutInflater;
    private Activity context;
    Gson gson;
    LocalShoppintCart localShoppintCart;


    public CartInListViewAdapter(Activity cartFragment, ShoppingCart shop) {
        this.context = cartFragment;
       // this.mCartFragment = cartFragment;
        this.shoppingCart = shop;
        layoutInflater = context.getLayoutInflater();
        gson=new Gson();
        localShoppintCart=new LocalShoppintCart(this.context);




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

    @Override
    public int getCount() {
        return this.shoppingCart.productCell.size();
    }

    @Override
    public Object getItem(int position) {
        return this.shoppingCart.productCell.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.shoppingCart.productCell.get(position).id;
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
                    int newQuanity=Utility.shoppingCart.productCell.get(position).quantity+1;

                    if (newQuanity<=Utility.shoppingCart.productCell.get(position).product.quantity) {

                        Utility.shoppingCart.productCell.get(position).quantity += 1;
                        CartInListViewAdapter.this.notifyDataSetChanged();
                        String cart = gson.toJson(Utility.shoppingCart.productCell);
                        localShoppintCart = new LocalShoppintCart(context);
                        localShoppintCart.setProductCart(cart);
                    }else {
                        MakeToast.showToast(context,"Maximum available quantity of this product already added in your cart");
                    }

                }
            });

            viewHolder.cartItemMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity=Utility.shoppingCart.productCell.get(position).quantity;
                    quantity--;

                    if(quantity<Utility.shoppingCart.productCell.get(position).product.minimumOrderQuantity){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Are u sure?");
                        alertDialogBuilder
                                .setMessage("If you select quantity less than "+Utility.shoppingCart.productCell.get(position).product.minimumOrderQuantity
                                        +", this product will be removed from the cart.")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Utility.shoppingCart.productCell.remove(position);
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

                        Utility.shoppingCart.productCell.get(position).quantity -= 1;
                    }

                    String cart=gson.toJson(Utility.shoppingCart.productCell);
                    localShoppintCart=new LocalShoppintCart(context);
                    localShoppintCart.setProductCart(cart);
                    CartInListViewAdapter.this.notifyDataSetChanged();




                }
            });

            viewHolder.cartItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.shoppingCart.productCell.remove(position);

                    String cart=gson.toJson(Utility.shoppingCart.productCell);
                    localShoppintCart=new LocalShoppintCart(context);
                    localShoppintCart.setProductCart(cart);
                    CartInListViewAdapter.this.notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            String icon = this.shoppingCart.productCell.get(position).product.pictures.get(0).name;
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

            viewHolder.cartItemName.setText(this.shoppingCart.productCell.get(position).product.title);
            viewHolder.cartItemPrice.setText("" + this.shoppingCart.productCell.get(position).product.prices.get(0).retailPrice+" "+Utility.CURRENCY_CODE);
            int quantity=this.shoppingCart.productCell.get(position).quantity;
            double price= this.shoppingCart.productCell.get(position).product.prices.get(0).retailPrice;
            double subTotal=quantity*price;
            viewHolder.singleSubTotal.setText(subTotal+" "+Utility.CURRENCY_CODE);
            viewHolder.quantityTextView.setText(String.valueOf(this.shoppingCart.productCell.get(position).quantity));
        }
        catch (Exception e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }



}
