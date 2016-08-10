package com.themallbd.workspaceit.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.themallbd.workspaceit.asynctask.CancelOrderAyncTask;
import com.themallbd.workspaceit.asynctask.SuccessOrderAsynTask;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

import java.math.BigDecimal;

public class PaypalPaymentActivity extends BaseActivityWithoutDrawer implements View.OnClickListener {

    private Button paypalButton;
    private TextView totalTextView,usdTextView;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId("<Clienr ID>");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_payment);

        totalTextView=(TextView)findViewById(R.id.amount_paypal_text_view);
        usdTextView=(TextView)findViewById(R.id.paypal_usd_text_view);

        totalTextView.setText(Utility.finishOrderSummary.order_grand_total+" BDT");
        usdTextView.setText(Utility.finishOrderSummary.paypal_pay_amount+" USD");
        paypalButton=(Button)findViewById(R.id.paypal_button);
        paypalButton.setOnClickListener(this);
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new CancelOrderAyncTask().execute(String.valueOf(Utility.finishOrderSummary.order_id));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }






    @Override
    public void onClick(View v) {
        if (v==paypalButton){
            PayPalPayment payment = new PayPalPayment(new BigDecimal(Utility.finishOrderSummary.paypal_pay_amount), "USD", "The Mall BD",
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            startActivityForResult(intent, 0);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            // The payment succeeded
            case Activity.RESULT_OK:

                new SuccessOrderAsynTask().execute(String.valueOf(Utility.finishOrderSummary.order_id));
                break;

            // The payment was canceled
            case Activity.RESULT_CANCELED:
                MakeToast.showToast(this,"Payment cancel");
                break;

            // The payment failed, get the error from the EXTRA_ERROR_ID and EXTRA_ERROR_MESSAGE
            case PaymentActivity.RESULT_EXTRAS_INVALID:
                MakeToast.showToast(this,"Invalid");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
