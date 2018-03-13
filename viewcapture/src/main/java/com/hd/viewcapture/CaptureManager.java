package com.hd.viewcapture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.hd.viewcapture.capture.Capture;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by hd on 2018/2/6 .
 * Capture Manager
 */
public final class CaptureManager<T> {

    private final String TAG = CaptureManager.class.getSimpleName();

    private T t;

    private Capture<T> capture;

    private Handler handler;

    private Bitmap bitmap;

    private String fileName, fileNameSuffix, directoryName;

    private int jpgQuality = 100;

    private OnSaveResultListener listener;

    private BitmapProcessor bitmapProcessor;

    void into(@NonNull T t, @NonNull Capture<T> capture) {
        this.t = t;
        this.capture = capture;
    }

    Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = capture.capture(t);
            if (bitmapProcessor != null && bitmap != null) {
                bitmap = bitmapProcessor.process(bitmap);
            }
        }
        return bitmap;
    }

    public CaptureManager setBitmapProcessor(BitmapProcessor bitmapProcessor) {
        this.bitmapProcessor = bitmapProcessor;
        return this;
    }

    void setFileNameSuffix(@NonNull String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }

    void setCompressJPGQuality(int jpgQuality) {
        this.jpgQuality = jpgQuality < 0 || jpgQuality > 100 ? 100 : jpgQuality;
    }

    public CaptureManager setFileName(@NonNull String fileName) {
        this.fileName = fileName;
        return this;
    }

    public CaptureManager setDirectoryName(@NonNull String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    public CaptureManager setOnSaveResultListener(OnSaveResultListener listener) {
        this.listener = listener;
        if (listener != null) {
            this.handler = new Handler(Looper.myLooper());
        } else if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        return this;
    }

    public void save() {
        if (Utils.isExternalStorageReady() && Utils.isPermissionGranted(getAppContext())) {
            AsyncSaveImage asyncSaveBitmap = new AsyncSaveImage(getAppContext(), getBitmap());
            asyncSaveBitmap.execute();
        } else {
            Log.e(TAG, "ViewCapture couldn't save. Make sure permission to write to storage is granted");
        }
    }

    private Context getAppContext() {
        if (t == null) {
            throw new NullPointerException("current view is null");
        } else {
            return ((View) t).getContext().getApplicationContext();
        }
    }

    private String getFileNameSuffix() {
        return fileNameSuffix;
    }

    private String getDirectoryName() {
        if (directoryName == null || directoryName.isEmpty()) {
            return Environment.DIRECTORY_PICTURES;
        } else {
            return directoryName;
        }
    }

    private String getFileName() {
        if (fileName == null || fileName.isEmpty()) {
            return createTimeStamp() + getFileNameSuffix();
        } else {
            return fileName + getFileNameSuffix();
        }
    }

    private String createTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public interface OnSaveResultListener {
        void onSaveResult(boolean isSaved, String path, Uri uri);
    }

    public interface BitmapProcessor {
        Bitmap process(Bitmap raw);
    }

    private void notifyListener(final boolean isSaved, final String path, final Uri uri) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onSaveResult(isSaved, path, uri);
                }
            });
        }
        recycleBitmap();
    }

    private void recycleBitmap() {
        if (bitmap != null)
            bitmap.recycle();
        bitmap = null;
    }


    @SuppressLint("StaticFieldLeak")
    private final class AsyncSaveImage extends AsyncTask<Void, Void, Void> //
            implements MediaScannerConnection.OnScanCompletedListener {

        private Context context;

        private Bitmap bitmap;

        private AsyncSaveImage(Context context, Bitmap bitmap) {
            this.context = context;
            this.bitmap = bitmap;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (bitmap == null) {
                notifyListener(false, null, null);
                return null;
            }
            File directoryFile = new File(Environment.getExternalStorageDirectory(), getDirectoryName());
            if (directoryFile.exists()) {
                saveBitmap(directoryFile);
            } else {
                if (directoryFile.mkdirs()) {
                    saveBitmap(directoryFile);
                } else {
                    notifyListener(false, null, null);
                }
            }
            return null;
        }

        private void saveBitmap(File directoryFile) {
            File imageFile = new File(directoryFile, getFileName());
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(imageFile))) {
                if (fileNameSuffix.contains("png")) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, jpgQuality, out);
                }
            } catch (Exception e) {
                notifyListener(false, null, null);
            }
            MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, this);
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            if (uri != null && path != null) {
                notifyListener(true, path, uri);
            } else {
                notifyListener(false, path, uri);
            }
        }
    }
}
