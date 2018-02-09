package com.hd.viewcapture.capture.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.hd.viewcapture.capture.Capture;


/**
 * Created by hd on 2018/2/9 .
 * activity window capture
 */
public class ActivityCapture implements Capture<Activity> {

    @Override
    public Bitmap capture(@NonNull Activity activity) {
        return null;
    }
}
