package com.hexagon.translator;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import im.delight.android.webview.AdvancedWebView;

public class Offers extends AppCompatActivity {
    AdvancedWebView advancedWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers);
        ProgressBar progressBar=findViewById(R.id.progress_horizontal);
        progressBar.getProgressDrawable().setColorFilter(Color.rgb(18,118,11), PorterDuff.Mode.SRC_IN);
         advancedWebView=findViewById(R.id.webview);
        advancedWebView.getSettings().setJavaScriptEnabled(true);
        advancedWebView.getSettings().setDomStorageEnabled(true);
        advancedWebView.getSettings().setDatabaseEnabled(true);
        advancedWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        advancedWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        advancedWebView.getSettings().setAllowFileAccess(true);
        advancedWebView.getSettings().setAllowContentAccess(true);
        advancedWebView.getSettings().setSupportZoom(true);
        advancedWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        advancedWebView.setMixedContentAllowed(true);
        advancedWebView.setCookiesEnabled(true);
        advancedWebView.setThirdPartyCookiesEnabled(true);
        advancedWebView.addHttpHeader("X-Requested-With", "Android Browser");
advancedWebView.loadUrl("http://0.0.0.0:8080");
        FrameLayout tabwebview=findViewById(R.id.tab_webview);
advancedWebView.setWebViewClient(new WebViewClient(){

});
advancedWebView.setWebChromeClient(new WebChromeClient(){
    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {

        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

        super.onProgressChanged(view, newProgress);
        progressBar.setProgress(newProgress);
        if(newProgress==100){
            progressBar.setVisibility(View.GONE);
        }
    }
});

    }


    @Override
    public void onBackPressed() {
        if(advancedWebView.canGoBack()){
            advancedWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
