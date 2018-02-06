package com.hd.viewcapture;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by hd on 2018/2/6 .
 * view capture
 */
public final class ViewCapture {

    @NonNull
    private static CaptureType getCaptureManager(@NonNull Object view) {
        CaptureType captureType =new CaptureType();
        if (view instanceof RecyclerView) {
            captureType.of((RecyclerView) view);
        } else if (view instanceof ListView) {
            captureType.of((ListView) view);
        } else if (view instanceof ScrollView) {
            captureType.of((ScrollView) view);
        } else if (view instanceof HorizontalScrollView) {
            captureType.of((HorizontalScrollView) view);
        } else if (view instanceof WebView) {
            captureType.of((WebView) view);
        } else {
            captureType.of((View) view);
        }
        return captureType;
    }

    @NonNull
    public static CaptureType with(@NonNull RecyclerView view) {
        return getCaptureManager(view);
    }

    @NonNull
    public static CaptureType with(@NonNull ListView view) {
        return getCaptureManager(view);
    }

    @NonNull
    public static CaptureType with(@NonNull ScrollView view) {
        return getCaptureManager(view);
    }

    @NonNull
    public static CaptureType with(@NonNull HorizontalScrollView view) {
        return getCaptureManager(view);
    }

    @NonNull
    public static CaptureType with(@NonNull WebView view) {
        return getCaptureManager(view);
    }

    @NonNull
    public static CaptureType with(@NonNull View view) {
        return getCaptureManager(view);
    }

}
