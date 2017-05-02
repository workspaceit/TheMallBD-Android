package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.themallbd.workspaceit.asynctask.ConfrimWalletMixPaymentAsyncTask;
import com.themallbd.workspaceit.asynctask.GetWalletMixURLAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.workspaceit.themall.R;

public class WalletMixPaymentWebViewActivity extends BaseActivityWithoutDrawer {
    private int orderId;
    private WebView browser;
    private InternetConnection internetConnection;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_mix_payment_web_view);
        browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());

        flag=false;
        orderId = getIntent().getIntExtra("order_id", -1);

        internetConnection = new InternetConnection(this);

        if (internetConnection.checkInternet()) {
            new GetWalletMixURLAsynTask(this).execute(String.valueOf(orderId));
        } else {
            Intent intent = new Intent(this, NoInternetActiviy.class);
            startActivity(intent);
            finish();
        }


    }


    public void setupWebView(String url) {

        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);

    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.v("web_error", error.toString());
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (url.contains("http://188.166.214.41/mallbdweb/public/walletmix/mobilecallback")){
                flag=true;
                new ConfrimWalletMixPaymentAsyncTask(WalletMixPaymentWebViewActivity.this).execute(String.valueOf(orderId));
            }
            super.onPageFinished(view, url);
        }

    }

    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else {

            this.finish();
            super.onBackPressed();
        }
    }



}
