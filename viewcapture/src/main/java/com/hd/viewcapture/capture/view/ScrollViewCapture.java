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
public class ScrollViewCapture extends Capture<ScrollView> {
    @Override
    public Bitmap capture(@NonNull ScrollView scrollView) {
        Bitmap bitmap = new VerticalScrollCaptureHelper<ScrollView>().scrollCapture(scrollView);
        report(bitmap);
        return bitmap;
    }
}
