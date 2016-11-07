package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.PrevoiusOrderActivity;
import com.themallbd.workspaceit.dataModel.Orders;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 8/5/2016.
 */
public class ProducstInOrderAdapter extends BaseAdapter {
    private Activity activity;
    private Orders order;
    private LayoutInflater layoutInflater;
    private  String productUrl = "product/general/";
    private String packageUrl = "package/general/";

    public ProducstInOrderAdapter(Activity activity,Orders order){
        this.activity=activity;
        this.order=order;
        this.layoutInflater=this.activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return order.orderProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return order.orderProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return order.orderProducts.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.product_in_orders_single_item,null);
            viewHolder = new ViewHolder();

            viewHolder.orderProductImageView = (ImageView) convertView.findViewById(R.id.order_item_imageView);
            viewHolder.productTitel=(TextView)convertView.findViewById(R.id.order_details_product_text_view);
            viewHolder.quantityTextView=(TextView)convertView.findViewById(R.id.order_product_quantity_text_view);
            viewHolder.priceTextView=(TextView)convertView.findViewById(R.id.order_price_text_view);
            viewHolder.subTotalTextView=(TextView)convertView.findViewById(R.id.order_sub_total_text_view);
            viewHolder.typeTextView=(TextView)convertView.findViewById(R.id.product_type_text_view);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {


            if (order.orderProducts.get(position).itemType.equals("product")){
                if(order.orderProducts.get(position).productitem.pictures.size()>0) {
                    ImageLoader.getInstance().displayImage(Utility.IMAGE_URL + productUrl + order.orderProducts.get(position).productitem.pictures.get(0).name,
                            viewHolder.orderProductImageView);
                }

                viewHolder.productTitel.setText(order.orderProducts.get(position).productitem.title);
                viewHolder.quantityTextView.setText(order.orderProducts.get(position).productQuantity+"");

                viewHolder.priceTextView.setText(order.orderProducts.get(position).price+" "+Utility.CURRENCY_CODE);
                viewHolder.typeTextView.setText("Product");
                float subtotal= (float) (order.orderProducts.get(position).productQuantity*order.orderProducts.get(position).price);
                viewHolder.subTotalTextView.setText(subtotal+" "+Utility.CURRENCY_CODE);

            }else {

                viewHolder.productTitel.setText(order.orderProducts.get(position).packageitem.packageTitle);
                viewHolder.quantityTextView.setText(order.orderProducts.get(position).packageQuantity+"");
                viewHolder.priceTextView.setText(order.orderProducts.get(position).price+" "+order.currencyCode);
                float subtotal= (float) (order.orderProducts.get(position).packageQuantity*order.orderProducts.get(position).price);
                viewHolder.subTotalTextView.setText(subtotal+" "+order.currencyCode);
                viewHolder.typeTextView.setText("Package");
                ImageLoader.getInstance().displayImage(Utility.IMAGE_URL+packageUrl+order.orderProducts.get(position).packageitem.image,
                        viewHolder.orderProductImageView);

            }



        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return convertView;
    }

    public class ViewHolder {

        public ImageView orderProductImageView;
        public TextView productTitel;
        public TextView typeTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public TextView subTotalTextView;



    }
}
