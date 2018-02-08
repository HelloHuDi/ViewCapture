package com.hd.viewcapture.capture.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hd on 2018/2/8 .
 * vertical Scroll Capture
 */
public class VerticalScrollCaptureHelper<T extends View> implements ScrollCaptureHelper<T> {

    @Override
    public Bitmap scrollCapture(T t) {
        int currentX = t.getScrollX();
        int currentY = t.getScrollY();
        try {
            enableSomething(t);
            return scrollCaptureView(t);
        } finally {
            restoreSomething(t, currentX, currentY);
        }
    }

    private Bitmap scrollCaptureView(@NonNull T t) {
        Bitmap b = getViewBpWithoutBottom(t);
        //the height of the scrollView that is visible
        int vh = t.getMeasuredHeight();
        //the total height of the scrollView
        int th = ((ViewGroup)t).getChildAt(0).getMeasuredHeight();
        Bitmap temp;
        //the total height is more than one screen
        int a = 0;
        if (th > vh) {
            int w = t.getMeasuredWidth();
            //max visible height
            int absVh = vh - t.getPaddingTop() - t.getPaddingBottom();
            do {
                a++;
                int restHeight = th - vh;
                if (restHeight <= absVh) {
                    if (a % 2 != 0) {
                        restHeight = restHeight - t.getPaddingTop() - t.getPaddingBottom();
                    } else {
                        restHeight = restHeight + t.getPaddingTop() + t.getPaddingBottom();
                    }
                    t.scrollBy(0, restHeight);
                    vh += restHeight;
                    temp = getViewBp(t);
                } else {
                    t.scrollBy(0, absVh);
                    if (a / 2 == 0) {
                        vh += absVh - t.getPaddingBottom();
                    } else {
                        vh += absVh;
                    }
                    temp = getViewBp(t);
                }
                b = mergeBitmap(t, b, vh, temp, w);
            } while (vh < th);
        }
        return b;
    }

    @NonNull
    private Bitmap mergeBitmap(@NonNull T t, Bitmap b, int vh, Bitmap temp, int w) {
        // create the new blank bitmap
        Bitmap newbmp = Bitmap.createBitmap(w, vh, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(newbmp);
        // draw bg into
        cv.drawBitmap(temp, 0, t.getScrollY(), null);
        // draw fg into
        cv.drawBitmap(b, 0, 0, null);
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);
        // store
        cv.restore();
        return newbmp;
    }

    private void restoreSomething(@NonNull T t, int currentX, int currentY) {
        t.scrollTo(currentX, currentY);
        t.setVerticalScrollBarEnabled(true);
        t.setVerticalFadingEdgeEnabled(true);
        t.setDrawingCacheEnabled(false);
        t.destroyDrawingCache();
    }

    private void enableSomething(@NonNull T t) {
        t.setVerticalScrollBarEnabled(false);
        t.setVerticalFadingEdgeEnabled(false);
        t.scrollTo(0, 0);
        t.setDrawingCacheEnabled(true);
        t.buildDrawingCache(true);
    }

    private Bitmap getViewBpWithoutBottom(@NonNull View v) {
        return getBitmap(v, true);
    }

    private Bitmap getViewBp(@NonNull View v) {
        return getBitmap(v, false);
    }

    private Bitmap getBitmap(@NonNull View v, boolean needBottom) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(
                    View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),//
                    View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(),//
                     (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),//
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), //
                                        v.getMeasuredHeight() - (needBottom ? v.getPaddingBottom() : 0));
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }
}
