package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by hd on 2018/2/6 .
 * View Capture
 */
public class DefaultViewCapture implements Capture<View> {

    @Override
    public Bitmap capture(View view) {
        return null;
    }
}
