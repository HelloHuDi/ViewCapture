package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hd.viewcapture.capture.Capture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hd on 2018/2/6 .
 * ListView Capture
 */
public class ListViewCapture implements Capture<ListView> {
    @Override
    public Bitmap capture(@NonNull ListView listView) {
        List<View> viewList = new ArrayList<>();
        try {
            ListAdapter adapter = listView.getAdapter();
            Drawable dividerDrawable = listView.getDivider();
            Drawable backgroundDrawable = listView.getBackground();
            int dividerHeight = listView.getDividerHeight();
            int itemsCount = adapter.getCount();
            int allHeight = listView.getPaddingTop() + listView.getPaddingBottom();
            int allWidth = listView.getMeasuredWidth() + listView.getPaddingLeft() + listView.getPaddingRight();
            for (int i = 0; i < adapter.getCount(); i++) {
                View childView = adapter.getView(i, null, listView);
                childView.measure(
                        View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),//
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                childView.setDrawingCacheEnabled(true);
                childView.buildDrawingCache();
                viewList.add(childView);
                allHeight += childView.getMeasuredHeight();
            }
            allHeight += (itemsCount - 1) * dividerHeight;
            Bitmap bigBitmap = Bitmap.createBitmap(allWidth, allHeight, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Paint paint = new Paint();
            int iHeight = listView.getPaddingTop();
            final Rect bounds = new Rect();
            bounds.set(0, 0, allWidth, allHeight);
            backgroundDrawable.setBounds(bounds);
            backgroundDrawable.draw(bigCanvas);
            for (int i = 0; i < viewList.size(); i++) {
                Bitmap bmp = viewList.get(i).getDrawingCache();
                bigCanvas.drawBitmap(bmp, listView.getPaddingLeft(), iHeight, paint);
                iHeight += bmp.getHeight();
                if (i < viewList.size() - 1 && dividerHeight > 0 && dividerDrawable != null) {
                    bounds.set(0, iHeight, allWidth, iHeight + dividerHeight);
                    dividerDrawable.setBounds(bounds);
                    dividerDrawable.draw(bigCanvas);
                    iHeight += dividerHeight;
                }
                bmp.recycle();
                bmp = null;
            }
            return bigBitmap;
        } finally {
            for (View view : viewList) {
                view.setDrawingCacheEnabled(false);
                view.destroyDrawingCache();
            }
            viewList.clear();
            viewList = null;
        }
    }
}
