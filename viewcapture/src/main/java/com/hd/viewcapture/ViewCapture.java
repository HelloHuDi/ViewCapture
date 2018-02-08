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
    private static CaptureType getCaptureManager(@NonNull Object o) {
        CaptureType captureType = new CaptureType();
        if (o instanceof RecyclerView) {
            captureType.of((RecyclerView) o);
        } else if (o instanceof ListView) {
            captureType.of((ListView) o);
        } else if (o instanceof ScrollView) {
            captureType.of((ScrollView) o);
        } else if (o instanceof HorizontalScrollView) {
            captureType.of((HorizontalScrollView) o);
        } else if (o instanceof WebView) {
            captureType.of((WebView) o);
        } else {
            captureType.of((View) o);
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
