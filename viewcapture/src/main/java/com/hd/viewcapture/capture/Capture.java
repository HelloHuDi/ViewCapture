package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by hd on 2018/2/6 .
 *
 */
public interface Capture<T extends View> {

     Bitmap capture(@NonNull T t);
}
