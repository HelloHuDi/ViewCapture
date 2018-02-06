package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.HorizontalScrollView;

/**
 * Created by hd on 2018/2/6 .
 * HorizontalScrollView Capture
 */
public class HorizontalScrollViewCapture implements Capture<HorizontalScrollView> {
    @Override
    public Bitmap capture(HorizontalScrollView horizontalScrollView) {
        int w = 0;
        for (int i = 0; i < horizontalScrollView.getChildCount(); i++) {
            w += horizontalScrollView.getChildAt(i).getWidth();
            //            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Bitmap bitmap = Bitmap.createBitmap(w,horizontalScrollView.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        horizontalScrollView.draw(canvas);
        return bitmap;
    }
}
