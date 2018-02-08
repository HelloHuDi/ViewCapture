package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends BaseCaptureActivity<WebView> {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView=findViewById(R.id.webView);
        webView.loadUrl("https://github.com/HelloHuDi");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void capture(View view){
        captureView(webView);
    }
}
