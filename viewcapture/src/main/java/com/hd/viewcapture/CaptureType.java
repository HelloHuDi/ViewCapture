package com.hd.viewcapture;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hd.viewcapture.capture.DefaultViewCapture;
import com.hd.viewcapture.capture.HorizontalScrollViewCapture;
import com.hd.viewcapture.capture.ListViewCapture;
import com.hd.viewcapture.capture.RecyclerViewCapture;
import com.hd.viewcapture.capture.ScrollViewCapture;
import com.hd.viewcapture.capture.WebViewCapture;

/**
 * Created by hd on 2018/2/6 .
 * capture type
 */
public final class CaptureType {

    private CaptureManager captureManager;

    CaptureType() {
        captureManager = new CaptureManager();
    }

    void of(@NonNull RecyclerView view) {
        captureManager.capture(view, new RecyclerViewCapture());
    }

    void of(@NonNull ListView view) {
        captureManager.capture(view, new ListViewCapture());
    }

    void of(@NonNull ScrollView view) {
        captureManager.capture(view, new ScrollViewCapture());
    }

    void of(@NonNull HorizontalScrollView view) {
        captureManager.capture(view, new HorizontalScrollViewCapture());
    }

    void of(@NonNull WebView view) {
        captureManager.capture(view, new WebViewCapture());
    }

    void of(@NonNull View view) {
        captureManager.capture(view, new DefaultViewCapture());
    }

    @NonNull
    public CaptureManager asPNG() {
        return addSuffix("png");
    }

    @NonNull
    public CaptureManager asJPG() {
        return addSuffix("jpg");
    }

    @NonNull
    public CaptureManager asHide() {
        return addSuffix("capture");
    }

    @NonNull
    public CaptureManager addSuffix(@NonNull String suffix) {
        suffix = suffix.startsWith(".") ? suffix : "." + suffix;
        captureManager.setFileNameSuffix(suffix);
        return captureManager;
    }

    @NonNull
    public Bitmap getBitmap() {
        return captureManager.getBitmap();
    }
}
