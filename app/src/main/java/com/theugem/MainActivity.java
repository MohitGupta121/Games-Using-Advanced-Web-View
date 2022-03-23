package com.theugem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private android.webkit.WebView myWebView;

    private ProgressBar progressBar;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        myWebView = findViewById(R.id.myWeb);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setMax(100);

//        AdsManager.fbInterstitialAds(this);
        AdsManager.showInterstitialAd(MainActivity.this, new AdListener() {
        });

        WebSettings webSettings = myWebView.getSettings();

        Intent intent = getIntent();
        final String weblink = "https://games.cdn.famobi.com/html5games/c/crowd-run-3d/v040/?fg_domain=play.famobi.com&fg_aid=A1000-1&fg_uid=28ab613b-9f40-4ab7-8f09-aa19f32e3660&fg_pid=4638e320-4444-4514-81c4-d80a8c662371&fg_beat=542&original_ref=https%3A%2F%2Fhtml5games.com%2F";
        myWebView.loadUrl(weblink);
        progressBar.setProgress(0);

        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setSupportMultipleWindows(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.notification_error_ssl_cert_invalid);
                builder.setPositiveButton("continue", (dialog, which) -> handler.proceed());
                builder.setNegativeButton("cancel", (dialog, which) -> handler.cancel());
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100)
                    progressBar.setVisibility(View.INVISIBLE);
                else
                    progressBar.setVisibility(View.VISIBLE);
                super.onProgressChanged(view, newProgress);
            }
        });


//        AudienceNetworkAds.initialize(this);
        LinearLayout adViewContainer = findViewById(R.id.banner_container);
//        AdView fbAdBanner = AdsManager.createFacebookBannarAd(this);
//        adViewContainer.addView(fbAdBanner);
        AdsManager.initialise(this);
        com.google.android.gms.ads.AdView mAdView = new AdView(this);
        adViewContainer.addView(mAdView);
        AdsManager.showBannerAd(mAdView, this);

    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            AdsManager.showInterstitialAd(MainActivity.this, new AdListener() {
            });
//            AdsManager.fbInterstitialAds(this);
            finish();
        }
    }
}
