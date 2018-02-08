package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ScrollView;

import com.hd.viewcapture.capture.Capture;
import com.hd.viewcapture.capture.helper.VerticalScrollCaptureHelper;

/**
 * Created by hd on 2018/2/6 .
 * scrollview capture
 */
public class ScrollViewCapture implements Capture<ScrollView> {
    @Override
    public Bitmap capture(@NonNull ScrollView scrollView) {
        return new VerticalScrollCaptureHelper<ScrollView>().scrollCapture(scrollView);
    }
}
