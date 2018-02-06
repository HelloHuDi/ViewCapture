package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ScrollView;

/**
 * Created by hd on 2018/2/6 .
 * scrollview capture
 */
public class ScrollViewCapture implements Capture<ScrollView> {
    @Override
    public Bitmap capture(ScrollView scrollView) {
        int h = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
//            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }
}
