package com.hd.viewcapture;

import android.app.Activity;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hd.viewcapture.capture.helper.CaptureCallback;

/**
 * Created by hd on 2018/2/6 .
 * view capture
 * be careful the view not to be too long, watch out for memory leaks {@link OutOfMemoryError}
 */
public final class ViewCapture {

    @NonNull
    private static <T> CaptureType<T> getCaptureManager(@NonNull T t) {
        CaptureType<T> captureType = new CaptureType<>();
        captureType.of(t);
        return captureType;
    }

    /**
     * capture RecyclerView
     * <p>
     * currently only support {@link LinearLayoutManager#VERTICAL}
     */
    @NonNull
    public static CaptureType<RecyclerView> with(@NonNull RecyclerView view) {
        return getCaptureManager(view);
    }

    /**
     * capture ListView
     */
    @NonNull
    public static CaptureType<ListView> with(@NonNull ListView view) {
        return getCaptureManager(view);
    }

    /**
     * capture ScrollView
     */
    @NonNull
    public static CaptureType<ScrollView> with(@NonNull ScrollView view) {
        return getCaptureManager(view);
    }

    /**
     * capture HorizontalScrollView
     */
    @NonNull
    public static CaptureType<HorizontalScrollView> with(@NonNull HorizontalScrollView view) {
        return getCaptureManager(view);
    }

    /**
     * capture WebView
     * <p>
     * take case of ,set {@link WebView#enableSlowWholeDocumentDraw()} on onCreate() method
     */
    @NonNull
    public static CaptureType<WebView> with(@NonNull WebView view) {
        return getCaptureManager(view);
    }

    /**
     * capture general view
     */
    @NonNull
    public static CaptureType<View> with(@NonNull View view) {
        return getCaptureManager(view);
    }

    /**
     * capture window by android 5.0+ API
     * <p>
     * {@link MediaProjectionManager}
     * {@link MediaProjection}
     * {@link VirtualDisplay}
     *
     * please choose {@link CaptureType#getBitmap(CaptureCallback)} when you call get bitmap
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    public static CaptureType<Activity> with(@NonNull Activity activity) {
        return getCaptureManager(activity);
    }

}
