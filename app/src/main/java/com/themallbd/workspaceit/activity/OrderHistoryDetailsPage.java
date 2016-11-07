package com.themallbd.workspaceit.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.themallbd.workspaceit.adapter.ProducstInOrderAdapter;
import com.themallbd.workspaceit.dataModel.Orders;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.view.CustomListView;
import com.workspaceit.themall.R;

public class OrderHistoryDetailsPage extends BaseActivityWithoutDrawer {
    private Toolbar toolbar;
    private TextView toolBarTitle;
    int position=0;
    private Orders order;
    private TextView subtotalTextView,shipmentFeeTextView,voucherDiscountTextview,productDiscountTextView,
            specailDiscountTextView,employeeDiscountTextView,totalPriceTextView;
    private CustomListView orderInProductListView;
    private ProducstInOrderAdapter producstInOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details_page);



        toolbar = (Toolbar) findViewById(R.id.toolbar);


        subtotalTextView=(TextView)findViewById(R.id.subtotal_order_details_text_view);
        shipmentFeeTextView=(TextView)findViewById(R.id.shipment_fee_order_details_text_view);
        voucherDiscountTextview=(TextView)findViewById(R.id.voucher_discount_order_details_text_view);
        productDiscountTextView=(TextView)findViewById(R.id.product_discount_order_details_text_view);
        specailDiscountTextView=(TextView)findViewById(R.id.specail_discount_order_details_text_view);
        employeeDiscountTextView=(TextView)findViewById(R.id.employee_discount_order_details_text_view);
        totalPriceTextView=(TextView)findViewById(R.id.total_order_text_view);

        orderInProductListView=(CustomListView)findViewById(R.id.products_in_order_list_view);

        position = getIntent().getIntExtra("position", -1);
        order=PrevoiusOrderActivity.ordersArrayList.get(position);
        producstInOrderAdapter= new ProducstInOrderAdapter(this,order);

        initializevalue();


    }

    private void initializevalue(){
        subtotalTextView.setText(order.orderTotal+" "+ Utility.CURRENCY_CODE);
        shipmentFeeTextView.setText(order.shipping_cost+" "+Utility.CURRENCY_CODE);
        voucherDiscountTextview.setText(order.voucher_discount+" "+Utility.CURRENCY_CODE);
        productDiscountTextView.setText(order.discount_total+" "+Utility.CURRENCY_CODE);
        specailDiscountTextView.setText(order.onpurchase_discount+" "+Utility.CURRENCY_CODE);
        employeeDiscountTextView.setText(order.employee_discount+" "+Utility.CURRENCY_CODE);

        float total=0;
        total=(order.orderTotal+order.shipping_cost)-(order.voucher_discount+order.discount_total+order.onpurchase_discount+order.employee_discount);
        totalPriceTextView.setText(total+" "+Utility.CURRENCY_CODE);

        orderInProductListView.setAdapter(producstInOrderAdapter);
        producstInOrderAdapter.notifyDataSetChanged();


    }
}
