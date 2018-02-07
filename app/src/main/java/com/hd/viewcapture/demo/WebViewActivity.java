package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends BaseCaptureActivity<WebView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }
}
