package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by hd on 2018/2/6 .
 *
 */
public interface Capture<T> {

     Bitmap capture(@NonNull T t);
}
