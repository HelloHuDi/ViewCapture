package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.hd.viewcapture.capture.Capture;

/**
 * Created by hd on 2018/2/6 .
 * WebView Capture
 */
public class WebViewCapture implements Capture<WebView> {
    @Override
    public Bitmap capture(@NonNull WebView webView) {
        return null;
    }
}
