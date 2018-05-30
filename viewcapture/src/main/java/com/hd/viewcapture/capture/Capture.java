package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hd.viewcapture.capture.helper.CaptureCallback;

/**
 * Created by hd on 2018/2/6 .
 *
 */
public abstract class Capture<T> {

    private CaptureCallback callback;

    public abstract Bitmap capture(@NonNull T t);

    public void injectCallback(@NonNull CaptureCallback callback){
        this.callback=callback;
    }

    protected void report(@Nullable Bitmap bitmap){
        if(callback!=null)callback.report(bitmap);
    }

}
