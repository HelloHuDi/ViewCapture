package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by hd on 2018/2/6 .
 * HorizontalScrollView Capture
 */
public class HorizontalScrollViewCapture implements Capture<HorizontalScrollView> {
    @Override
    public Bitmap capture(@NonNull HorizontalScrollView hsv) {
        int currentX = hsv.getScrollX();
        int currentY = hsv.getScrollY();
        try {
            enableSomething(hsv);
            Bitmap b = getViewBpWithoutBottom(hsv);
            //the width of the HorizontalScrollView that is visible
            int vW = hsv.getMeasuredWidth();
            //the total width of the HorizontalScrollView
            int tW = hsv.getChildAt(0).getMeasuredWidth();
            Bitmap temp;
            //the total width is more than one screen
            int a = 0;
            if (tW > vW) {
                int h = hsv.getMeasuredHeight();
                //max visible width
                int absVh = vW - hsv.getPaddingLeft() - hsv.getPaddingRight();
                do {
                    a++;
                    int restWidth = tW - vW;
                    if (restWidth <= absVh) {
                        if (a % 2 != 0) {
                            restWidth = restWidth - hsv.getPaddingLeft() - hsv.getPaddingRight();
                        } else {
                            restWidth = restWidth + hsv.getPaddingLeft() + hsv.getPaddingRight();
                        }
                        hsv.scrollBy(restWidth, 0);
                        vW += restWidth;
                        temp = getViewBp(hsv);
                    } else {
                        hsv.scrollBy(absVh, 0);
                        if (a / 2 == 0) {
                            vW += absVh - hsv.getPaddingRight();
                        } else {
                            vW += absVh;
                        }
                        temp = getViewBp(hsv);
                    }
                    b = mergeBitmap(hsv, b, vW, temp, h);
                } while (vW < tW);
            }
            return b;
        } finally {
            restoreSomething(hsv, currentX, currentY);
        }
    }

    @NonNull
    private Bitmap mergeBitmap(@NonNull HorizontalScrollView hsv, Bitmap b, int vW, Bitmap temp, int h) {
        Bitmap newbmp = Bitmap.createBitmap(vW, h, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(newbmp);
        // draw bg into
        cv.drawBitmap(temp, hsv.getScrollX(), 0, null);
        // draw fg into
        cv.drawBitmap(b, 0, 0, null);
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);
        // store
        cv.restore();
        return newbmp;
    }

    private void restoreSomething(@NonNull HorizontalScrollView hsv, int currentX, int currentY) {
        hsv.scrollTo(currentX, currentY);
        hsv.setHorizontalFadingEdgeEnabled(true);
        hsv.setHorizontalScrollBarEnabled(true);
        hsv.setDrawingCacheEnabled(false);
        hsv.destroyDrawingCache();
    }

    private void enableSomething(@NonNull HorizontalScrollView hsv) {
        hsv.setHorizontalFadingEdgeEnabled(false);
        hsv.setHorizontalScrollBarEnabled(false);
        hsv.scrollTo(0, 0);
        hsv.setDrawingCacheEnabled(true);
        hsv.buildDrawingCache(true);
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
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, //
            v.getMeasuredWidth() - (needRight ? v.getPaddingRight() : 0), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

}
