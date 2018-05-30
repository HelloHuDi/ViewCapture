package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;

import com.hd.viewcapture.capture.Capture;

/**
 * Created by hd on 2018/2/6 .
 * RecyclerView capture
 */
public class RecyclerViewCapture extends Capture<RecyclerView> {

    @Override
    public Bitmap capture(@NonNull RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = recyclerView.getPaddingTop() + recyclerView.getPaddingBottom();
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, View> viewCache = new LruCache<>(cacheSize);
            int allWidth = recyclerView.getMeasuredWidth() + recyclerView.getPaddingLeft() + recyclerView.getPaddingRight();
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY), //
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                viewCache.put(String.valueOf(i), holder.itemView);
                int itemHeight = holder.itemView.getMeasuredHeight();
                int[] margin = getMargin(holder.itemView);
                height += itemHeight + margin[0] + margin[1];
            }
            bigBitmap = Bitmap.createBitmap(allWidth, height, Bitmap.Config.RGB_565);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = recyclerView.getBackground();
            final Rect bounds = new Rect();
            bounds.set(0, 0, allWidth, height);
            lBackground.setBounds(bounds);
            lBackground.draw(bigCanvas);
            Paint paint = new Paint();
            int iHeight = recyclerView.getPaddingTop();
            for (int i = 0; i < size; i++) {
                View view = viewCache.get(String.valueOf(i));
                int[] margin = getMargin(view);
                int topMargin = margin[0];
                int bottomMargin = +margin[1];
                Bitmap bitmap = view.getDrawingCache();
                bigCanvas.drawBitmap(bitmap, recyclerView.getPaddingLeft(), iHeight + topMargin, paint);
                iHeight += view.getHeight() + topMargin + bottomMargin;
                view.setDrawingCacheEnabled(false);
                view.destroyDrawingCache();
                bitmap.recycle();
                bitmap = null;
            }
        }
        report(bigBitmap);
        return bigBitmap;
    }

    private int[] getMargin(View itemView) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        return new int[]{marginLayoutParams.topMargin, marginLayoutParams.topMargin};
    }
}
