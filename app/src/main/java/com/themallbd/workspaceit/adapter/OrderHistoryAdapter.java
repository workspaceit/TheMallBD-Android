package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.themallbd.workspaceit.activity.PrevoiusOrderActivity;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 8/4/2016.
 */
public class OrderHistoryAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;

    public OrderHistoryAdapter(Activity activity){
        this.activity=activity;
        layoutInflater=activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return PrevoiusOrderActivity.ordersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return PrevoiusOrderActivity.ordersArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return PrevoiusOrderActivity.ordersArrayList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.order_history_single_item, null);
            viewHolder = new ViewHolder();

            viewHolder.counterTextView = (TextView) convertView.findViewById(R.id.counter_order_history);
            viewHolder.invoiceNymberTextView = (TextView) convertView.findViewById(R.id.invoice_number_text_view);
            viewHolder.orderTotalTextView = (TextView) convertView.findViewById(R.id.order_total_price_text_view);
            viewHolder.shippingAdressTextView=(TextView)convertView.findViewById(R.id.order_history_shipping_address);
            viewHolder.orderDate=(TextView)convertView.findViewById(R.id.order_date_text_view);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        try {

           viewHolder.counterTextView.setText((position+1)+"");
            viewHolder.invoiceNymberTextView.setText(PrevoiusOrderActivity.ordersArrayList.get(position).invoiceNo);
            viewHolder.orderTotalTextView.setText(PrevoiusOrderActivity.ordersArrayList.get(position).orderTotal+" "+
                    PrevoiusOrderActivity.ordersArrayList.get(position).currencyCode);
            viewHolder.shippingAdressTextView.setText(PrevoiusOrderActivity.ordersArrayList.get(position).shippingAddress);
            viewHolder.orderDate.setText(PrevoiusOrderActivity.ordersArrayList.get(position).orderDate);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        if (position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        return convertView;
    }



    private class ViewHolder {

        public TextView counterTextView;
        public TextView invoiceNymberTextView;
        public TextView orderTotalTextView;
        public TextView shippingAdressTextView;
        public TextView orderDate;

    }
}
