package com.hd.viewcapture.capture.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hd on 2018/2/8 .
 * horizontal Scroll Capture
 */
public class HorizontalScrollCaptureHelper<T extends View> implements ScrollCaptureHelper<T> {

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
        //the width of the T that is visible
        int vW = t.getMeasuredWidth();
        //the total width of the T
        int tW = ((ViewGroup) t).getChildAt(0).getMeasuredWidth();
        Bitmap temp;
        //the total width is more than one screen
        int a = 0;
        if (tW > vW) {
            int h = t.getMeasuredHeight();
            //max visible width
            int absVh = vW - t.getPaddingLeft() - t.getPaddingRight();
            do {
                a++;
                int restWidth = tW - vW;
                if (restWidth <= absVh) {
                    if (a % 2 != 0) {
                        restWidth = restWidth - t.getPaddingLeft() - t.getPaddingRight();
                    } else {
                        restWidth = restWidth + t.getPaddingLeft() + t.getPaddingRight();
                    }
                    t.scrollBy(restWidth, 0);
                    vW += restWidth;
                    temp = getViewBp(t);
                } else {
                    t.scrollBy(absVh, 0);
                    if (a / 2 == 0) {
                        vW += absVh - t.getPaddingRight();
                    } else {
                        vW += absVh;
                    }
                    temp = getViewBp(t);
                }
                b = mergeBitmap(t, b, vW, temp, h);
            } while (vW < tW);
        }
        return b;
    }

    @NonNull
    private Bitmap mergeBitmap(@NonNull T t, Bitmap b, int vW, Bitmap temp, int h) {
        Bitmap newbmp = Bitmap.createBitmap(vW, h, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(newbmp);
        // draw bg into
        cv.drawBitmap(temp, t.getScrollX(), 0, null);
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
        t.setHorizontalFadingEdgeEnabled(true);
        t.setHorizontalScrollBarEnabled(true);
        t.setDrawingCacheEnabled(false);
        t.destroyDrawingCache();
    }

    private void enableSomething(@NonNull T t) {
        t.setHorizontalFadingEdgeEnabled(false);
        t.setHorizontalScrollBarEnabled(false);
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

    private Bitmap getBitmap(@NonNull View v, boolean needRight) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        v.measure(
                View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),//
                View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
        v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(),//
                 (int) v.getY() + v.getMeasuredHeight());
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, //
                                        v.getMeasuredWidth() - (needRight ? v.getPaddingRight() : 0), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

}
