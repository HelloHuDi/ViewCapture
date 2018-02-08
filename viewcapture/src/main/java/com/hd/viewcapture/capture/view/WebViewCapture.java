package com.hd.viewcapture.capture.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;

import com.hd.viewcapture.capture.Capture;

/**
 * Created by hd on 2018/2/6 .
 * WebView Capture
 */
public class WebViewCapture implements Capture<WebView> {
    @Override
    public Bitmap capture(@NonNull WebView webView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return captureWebView(webView);
        }
        return captureWebView2(webView);
    }

    private Bitmap captureWebView(WebView webView) {
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        if (width > 0 && height > 0) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            picture.draw(canvas);
            return bitmap;
        }
        return null;
    }

    @SuppressLint("WrongCall")
    private Bitmap captureWebView2(@NonNull final WebView view) {
        openCache(view);
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);
        if (view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0) {
            return null;
        }
        Bitmap bm;
        try {
            bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            System.gc();
            try {
                bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.RGB_565);
            } catch (OutOfMemoryError ee) {
                closeCache(view);
                return captureWebView3(view);
            }
        }
        Canvas bigCanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigCanvas.drawBitmap(bm, 0, iHeight, paint);
        view.draw(bigCanvas);
        closeCache(view);
        return bm;
    }

    private Bitmap captureWebView3(WebView wv) {
        try {
            float scale = wv.getScale();
            int height = (int) (wv.getContentHeight() * scale + 0.5);
            Bitmap bitmap = Bitmap.createBitmap(wv.getWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            wv.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            try {
                openCache(wv);
                return wv.getDrawingCache();
            } finally {
                closeCache(wv);
            }
        }
    }

    private void openCache(@NonNull WebView view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
    }

    private void closeCache(@NonNull WebView view) {
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
    }

}
