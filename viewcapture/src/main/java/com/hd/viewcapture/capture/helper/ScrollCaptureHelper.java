package com.hd.viewcapture.capture.helper;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by hd on 2018/2/8 .
 * Scroll Capture
 */
public interface ScrollCaptureHelper<T extends View> {

    /**
     * T must have a scrolling attribute
     */
    Bitmap scrollCapture(T t);

}
