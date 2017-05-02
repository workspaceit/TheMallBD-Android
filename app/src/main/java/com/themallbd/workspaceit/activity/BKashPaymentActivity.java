package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.themallbd.workspaceit.asynctask.CancelOrderAyncTask;
import com.themallbd.workspaceit.asynctask.ConfrimaBKashPaymentAsyncTask;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class BKashPaymentActivity extends BaseActivityWithoutDrawer implements View.OnClickListener {

    private String tranxId;
    private TextView orderTotalTextView,bkashChargeTextView,totalWithChargeTextView;
    private Button payNowButton,backToStoreButton;
    private EditText senderMobileNumberET,trxIdET;
    private final double BKASH_CHARGE= 0.02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash_payment);

        orderTotalTextView=(TextView)findViewById(R.id.amount_bkash_text_view);
        bkashChargeTextView=(TextView)findViewById(R.id.bkash_charge_text_view);
        totalWithChargeTextView=(TextView)findViewById(R.id.with_baksh_total_text_view);

        payNowButton=(Button)findViewById(R.id.bkash_pay_now_button);
        backToStoreButton=(Button)findViewById(R.id.back_to_store_button);

        payNowButton.setOnClickListener( this);
        backToStoreButton.setOnClickListener(this);

        senderMobileNumberET=(EditText)findViewById(R.id.sender_mobile_number_edit_text);
        trxIdET=(EditText)findViewById(R.id.trx_id_edit_text);

        tranxId=getIntent().getStringExtra("trnx_Id");

        this.initialize();
    }

    private void initialize(){
        orderTotalTextView.setText(Utility.finishOrderSummary.order_grand_total + " BDT");
        double charge=Utility.finishOrderSummary.order_grand_total*BKASH_CHARGE;
        double totalWithCharge=charge+Utility.finishOrderSummary.order_grand_total;
        bkashChargeTextView.setText(charge+" BDT");
        totalWithChargeTextView.setText(totalWithCharge + " BDT");
    }

    @Override
    public void onClick(View v) {
        if (v==payNowButton){
            if (checkInput()){
                new ConfrimaBKashPaymentAsyncTask(this).execute(String.valueOf(tranxId),senderMobileNumberET.getText().toString(),
                        trxIdET.getText().toString());
            }

        }else if (v==backToStoreButton){
            new CancelOrderAyncTask().execute(String.valueOf(Utility.finishOrderSummary.order_id));

            Intent intent=new Intent(this,CheckoutActivity.class);
            startActivity(intent);
        }

    }


    private boolean checkInput(){
        if (senderMobileNumberET.getText().length()!=11 || senderMobileNumberET.getText().toString().equals("")){
            MakeToast.showToast(this, "Your BKash number is not valid");
            senderMobileNumberET.requestFocus();
            return false;
        }else if(trxIdET.getText().toString().equals("") || trxIdET.getText().toString().length()!=10){
            MakeToast.showToast(this,"TrxID is not valid");
            trxIdET.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
