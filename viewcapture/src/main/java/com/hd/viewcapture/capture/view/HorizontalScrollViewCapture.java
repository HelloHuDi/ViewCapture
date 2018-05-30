package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.HorizontalScrollView;

import com.hd.viewcapture.capture.Capture;
import com.hd.viewcapture.capture.helper.HorizontalScrollCaptureHelper;

/**
 * Created by hd on 2018/2/6 .
 * HorizontalScrollView Capture
 */
public class HorizontalScrollViewCapture extends Capture<HorizontalScrollView> {
    @Override
    public Bitmap capture(@NonNull HorizontalScrollView horizontalScrollView) {
        Bitmap bitmap = new HorizontalScrollCaptureHelper<HorizontalScrollView>().scrollCapture(horizontalScrollView);
        report(bitmap);
        return bitmap;
    }
}
