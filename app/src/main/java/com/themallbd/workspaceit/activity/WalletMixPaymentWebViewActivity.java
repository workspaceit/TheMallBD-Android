package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.content.SyncAdapterType;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.themallbd.workspaceit.asynctask.GetWalletMixURLAsynTask;
import com.themallbd.workspaceit.service.InternetConnection;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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


        orderId = getIntent().getIntExtra("order_id", -1);

        internetConnection = new InternetConnection(this);

        if (internetConnection.isConnectingToInternet()) {
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


            super.onPageFinished(view, url);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }



}
