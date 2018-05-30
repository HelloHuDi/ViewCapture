package com.hd.viewcapture.capture.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hd.viewcapture.capture.view.WindowCapture;

import java.nio.ByteBuffer;


/**
 * Created by hd on 2018/5/29 .
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WindowCaptureFragment extends Fragment {

    private final String TAG = WindowCaptureFragment.class.getSimpleName();

    private WindowCapture.WindowCaptureCallback callback;

    private final int REQUEST_MEDIA_PROJECTION = 1;

    private int mScreenDensity;

    private int windowWidth;
    private int windowHeight;

    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionManager mMediaProjectionManager;
    private ImageReader mImageReader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        windowWidth = metrics.widthPixels;
        windowHeight = metrics.heightPixels;
        mImageReader = ImageReader.newInstance(windowWidth, windowHeight, PixelFormat.RGBA_8888, 2);
        mMediaProjectionManager = (MediaProjectionManager) getActivity().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    public void prepare(@NonNull WindowCapture.WindowCaptureCallback callback) {
        this.callback = callback;
        if (mMediaProjectionManager != null) {
            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        } else {
            callback.report(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode != Activity.RESULT_OK) {
                Log.i(TAG, "User cancelled");
                callback.report(false);
                return;
            }
            Log.i(TAG, "Starting screen capture");
            mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            callback.report(true);
        }
    }

    public Bitmap capture() {
        try {
            mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG, windowWidth, windowHeight, mScreenDensity, //
                                                                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,//
                                                                    mImageReader.getSurface(), null, null);
            Log.i(TAG, "screen capture completed");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return createBitmap();
        } finally {
            release();
        }
    }

    private Bitmap createBitmap() {
        Image image = mImageReader.acquireLatestImage();
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        image.close();
        return bitmap;
    }

    private void release() {
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
    }
}
