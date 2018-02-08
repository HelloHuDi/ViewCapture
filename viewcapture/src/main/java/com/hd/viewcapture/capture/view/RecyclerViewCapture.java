package com.hd.viewcapture.capture.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hd.viewcapture.capture.Capture;
import com.hd.viewcapture.capture.helper.VerticalScrollCaptureHelper;

/**
 * Created by hd on 2018/2/6 .
 * RecyclerView capture
 */
public class RecyclerViewCapture implements Capture<RecyclerView> {
    @Override
    public Bitmap capture(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (layoutManager.canScrollVertically()) {
                Log.d("GridLayoutManager", "vertical scroll");
                return new VerticalScrollCaptureHelper<RecyclerView>().scrollCapture(recyclerView);
            } else if (layoutManager.canScrollHorizontally()) {
                Log.d("GridLayoutManager", "horizontal scroll");
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (layoutManager.canScrollVertically()) {
                Log.d("LinearLayoutManager", "vertical scroll");
                return new VerticalScrollCaptureHelper<RecyclerView>().scrollCapture(recyclerView);
            } else if (layoutManager.canScrollHorizontally()) {
                Log.d("LinearLayoutManager", "horizontal scroll");
            }
        } else {

        }
        return null;
    }

    /*
    @Override
    public Bitmap capture(@NonNull RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY), //
                                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }
            bigBitmap = Bitmap.createBitmap(recyclerView.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = recyclerView.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }
            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }*/
}
