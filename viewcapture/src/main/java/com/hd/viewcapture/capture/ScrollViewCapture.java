package com.hd.viewcapture.capture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by hd on 2018/2/6 .
 * scrollview capture
 * http://www.cnblogs.com/BoBoMEe/p/4556917.html
 */
public class ScrollViewCapture implements Capture<ScrollView> {
    @Override
    public Bitmap capture(@NonNull ScrollView sv) {
        int currentX = sv.getScrollX();
        int currentY = sv.getScrollY();
        try {
            enableSomething(sv);
            Bitmap b = getViewBpWithoutBottom(sv);
            //the height of the scrollView that is visible
            int vh = sv.getMeasuredHeight();
            //the total height of the scrollView
            int th = sv.getChildAt(0).getMeasuredHeight();
            Bitmap temp;
            //the total height is more than one screen
            int a = 0;
            if (th > vh) {
                int w = sv.getMeasuredWidth();
                //max visible height
                int absVh = vh - sv.getPaddingTop() - sv.getPaddingBottom();
                do {
                    a++;
                    int restHeight = th - vh;
                    if (restHeight <= absVh) {
                        if (a % 2 != 0) {
                            restHeight = restHeight - sv.getPaddingTop() - sv.getPaddingBottom();
                        } else {
                            restHeight = restHeight + sv.getPaddingTop() + sv.getPaddingBottom();
                        }
                        sv.scrollBy(0, restHeight);
                        vh += restHeight;
                        temp = getViewBp(sv);
                    } else {
                        sv.scrollBy(0, absVh);
                        if (a / 2 == 0) {
                            vh += absVh - sv.getPaddingBottom();
                        } else {
                            vh += absVh;
                        }
                        temp = getViewBp(sv);
                    }
                    b = mergeBitmap(vh, w, temp, 0, sv.getScrollY(), b, 0, 0);
                } while (vh < th);
            }
            return b;
        } finally {
            restoreSomething(sv, currentX, currentY);
        }
    }

    private void restoreSomething(@NonNull ScrollView sv, int currentX, int currentY) {
        sv.scrollTo(currentX, currentY);
        sv.setVerticalScrollBarEnabled(true);
        sv.setVerticalFadingEdgeEnabled(true);
        sv.setDrawingCacheEnabled(false);
        sv.destroyDrawingCache();
    }

    private void enableSomething(@NonNull ScrollView sv) {
        sv.setVerticalScrollBarEnabled(false);
        sv.setVerticalFadingEdgeEnabled(false);
        sv.scrollTo(0, 0);
        sv.setDrawingCacheEnabled(true);
        sv.buildDrawingCache(true);
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

    private Bitmap mergeBitmap(int newImageH, int newIamgeW, Bitmap background,//
                               float backX, float backY, Bitmap foreground, float foreX, float foreY) {
        // create the new blank bitmap
        Bitmap newbmp = Bitmap.createBitmap(newIamgeW, newImageH, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(newbmp);
        // draw bg into
        cv.drawBitmap(background, backX, backY, null);
        // draw fg into
        cv.drawBitmap(foreground, foreX, foreY, null);
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);
        // store
        cv.restore();
        return newbmp;
    }

}
