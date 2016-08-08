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

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

import java.math.BigDecimal;

public class PaypalPaymentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "credentials from developer.paypal.com";
    private static final int PAYPAL_BUTTON_ID =1 ;
    private CheckoutButton launchPayPalButton;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_payment);
        this.initLibrary();
        this.showPayPalButton();


    }

    public void initLibrary() {
        PayPal pp = PayPal.getInstance();

        if (pp == null) {  // Test to see if the library is already initialized

            // This main initialization call takes your Context, AppID, and target server
            pp = PayPal.initWithAppID(this, "APP-80W284485P519543T", PayPal.ENV_NONE);

            // Required settings:

            // Set the language for the library
            pp.setLanguage("en_US");

            // Some Optional settings:

            // Sets who pays any transaction fees. Possible values are:
            // FEEPAYER_SENDER, FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and FEEPAYER_SECONDARYONLY
            pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);

            // true = transaction requires shipping
            pp.setShippingEnabled(true);
        }
    }


    private void showPayPalButton() {

        // Generate the PayPal checkout button and save it for later use
        PayPal pp = PayPal.getInstance();
        launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_278x43, CheckoutButton.TEXT_PAY);

        // The OnClick listener for the checkout button
        launchPayPalButton.setOnClickListener(this);

        // Add the listener to the layout
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 10;
        launchPayPalButton.setLayoutParams(params);
        //launchPayPalButton.setId(PAYPAL_BUTTON_ID);
        ((RelativeLayout) findViewById(R.id.RelativeLayout01)).addView(launchPayPalButton);
        ((RelativeLayout) findViewById(R.id.RelativeLayout01)).setGravity(Gravity.CENTER_HORIZONTAL);
    }





    @Override
    public void onClick(View v) {
        MakeToast.showToast(this,"clicked");
        // Create a basic PayPal payment
        PayPalPayment payment = new PayPalPayment();

        // Set the currency type
        payment.setCurrencyType("USD");

        // Set the recipient for the payment (can be a phone number)
        payment.setRecipient("ppalav_1285013097_biz@yahoo.com");

        // Set the payment amount, excluding tax and shipping costs
        payment.setSubtotal(new BigDecimal(1.2));

        // Set the payment type--his can be PAYMENT_TYPE_GOODS,
        // PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE
        payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);

        // PayPalInvoiceData can contain tax and shipping amounts, and an
        // ArrayList of PayPalInvoiceItem that you can fill out.
        // These are not required for any transaction.
        PayPalInvoiceData invoice = new PayPalInvoiceData();

        // Set the tax amount
        invoice.setTax(new BigDecimal(0.0));

        Intent checkoutIntent =new Intent(this,PaymentActivity.class);

        startActivityForResult(checkoutIntent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            // The payment succeeded
            case Activity.RESULT_OK:

                MakeToast.showToast(this,"succes");
                break;

            // The payment was canceled
            case Activity.RESULT_CANCELED:
                MakeToast.showToast(this,"Payment cancel");
                break;

            // The payment failed, get the error from the EXTRA_ERROR_ID and EXTRA_ERROR_MESSAGE
            case PayPalActivity.RESULT_FAILURE:
                MakeToast.showToast(this,"Failure");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
