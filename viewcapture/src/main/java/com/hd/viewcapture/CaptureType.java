package com.hd.viewcapture;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hd.viewcapture.capture.Capture;
import com.hd.viewcapture.capture.view.DefaultViewCapture;
import com.hd.viewcapture.capture.view.HorizontalScrollViewCapture;
import com.hd.viewcapture.capture.view.ListViewCapture;
import com.hd.viewcapture.capture.view.RecyclerViewCapture;
import com.hd.viewcapture.capture.view.ScrollViewCapture;
import com.hd.viewcapture.capture.view.WebViewCapture;

/**
 * Created by hd on 2018/2/6 .
 * capture type
 */
public final class CaptureType<T> {

    private CaptureManager<T> captureManager;

    CaptureType() {
        captureManager = new CaptureManager<>();
    }

    void of(T t) {
        Capture capture;
        if (t instanceof RecyclerView) {
            capture = new RecyclerViewCapture();
        } else if (t instanceof ListView) {
            capture = new ListViewCapture();
        } else if (t instanceof ScrollView) {
            capture = new ScrollViewCapture();
        } else if (t instanceof HorizontalScrollView) {
            capture = new HorizontalScrollViewCapture();
        } else if (t instanceof WebView) {
            capture = new WebViewCapture();
        } else {
            capture = new DefaultViewCapture();
        }
        captureManager.into(t, capture);
    }

    @NonNull
    public CaptureManager asPNG() {
        return addSuffix("png");
    }

    @NonNull
    public CaptureManager asJPG() {
        return asJPG(100);
    }

    @NonNull
    public CaptureManager asJPG(int jpgQuality) {
        captureManager.setCompressJPGQuality(jpgQuality);
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

    public Bitmap getBitmap() {
        return captureManager.getBitmap();
    }
}
