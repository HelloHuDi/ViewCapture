package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

import com.hd.viewcapture.capture.Capture;

/**
 * Created by hd on 2018/2/6 .
 * View Capture
 */
public class DefaultViewCapture implements Capture<View> {

    @Override
    public Bitmap capture(@NonNull View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        canvas.setBitmap(null);
        return bitmap;
    }
}
