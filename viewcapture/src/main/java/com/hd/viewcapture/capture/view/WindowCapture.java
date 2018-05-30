package com.hd.viewcapture.capture.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;

import com.hd.viewcapture.capture.Capture;
import com.hd.viewcapture.capture.helper.WindowCaptureFragment;


/**
 * Created by hd on 2018/5/29 .
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WindowCapture extends Capture<Activity> {

    private final String TAG = "Window-Capture";

    private WindowCaptureFragment windowCaptureFragment;

    public interface WindowCaptureCallback {

        void report(boolean state);
    }

    private WindowCaptureFragment getWindowCaptureFragment(@NonNull Activity activity) {
        WindowCaptureFragment windowCaptureFragment = findScreenCaptureFragment(activity);
        boolean isNewInstance = windowCaptureFragment == null;
        if (isNewInstance) {
            windowCaptureFragment = new WindowCaptureFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().add(windowCaptureFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return windowCaptureFragment;
    }

    private WindowCaptureFragment findScreenCaptureFragment(@NonNull Activity activity) {
        return (WindowCaptureFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    @Override
    public Bitmap capture(@NonNull Activity activity) {
        windowCaptureFragment = getWindowCaptureFragment(activity);
        windowCaptureFragment.prepare(new WindowCaptureCallback() {
            @Override
            public void report(boolean state) {
                if (state) {
                    WindowCapture.this.report(windowCaptureFragment.capture());
                } else {
                    WindowCapture.this.report(null);
                }
            }
        });
        return null;
    }

}
