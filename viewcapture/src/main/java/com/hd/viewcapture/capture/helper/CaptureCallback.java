package com.hd.viewcapture.capture.helper;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/**
 * Created by hd on 2018/5/30 .
 */
public interface CaptureCallback {

    void report(@Nullable Bitmap bitmap);
}
