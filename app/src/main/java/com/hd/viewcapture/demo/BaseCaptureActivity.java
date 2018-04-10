package com.hd.viewcapture.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by hd on 2018/2/7 .
 * base capture
 */
public abstract class BaseCaptureActivity<V extends View> extends BaseActivity //
        implements CaptureManager.OnSaveResultListener, CaptureManager.BitmapProcessor {

    private boolean save_to_file = true;

    private Uri uri;

    private final String directoryName="viewCaptureFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uri = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_capture_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        save_to_file = item.getItemId() == R.id.saveFile;
        return super.onOptionsItemSelected(item);
    }

    public void lookCapture(View view) {
        if (uri != null) {
            Intent intent = new Intent(this, CaptureBitmapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    protected void captureView(V v) {
        uri = null;
        if (save_to_file) {
            ViewCapture.with(v)//
                       .asJPG(80)//
                       .setFileName("viewCapture")//
                       .setDirectoryName(directoryName)//
                       .setOnSaveResultListener(this)//
                       .setBitmapProcessor(this)//
                       .save();
        } else {
            loadBitmap(ViewCapture.with(v).getBitmap());
        }
    }

    @Override
    public void onSaveResult(boolean isSaved, String path, Uri uri) {
        String logStr = "save result :" + isSaved + "\n" + path + "\n" + uri;
        this.uri = uri;
        Log.d("tag", logStr);
        Toast.makeText(this, logStr, Toast.LENGTH_SHORT).show();
        createLuban(path,uri);
    }

    private void createLuban(final String path, final Uri uri) {
        File directoryFile = new File(Environment.getExternalStorageDirectory()+File.separator+directoryName, "compress");
        if (!directoryFile.exists()) {
            boolean dirs=directoryFile.mkdirs();
        }
        Luban.with(getApplicationContext())//
             .load(path) //
             .ignoreBy(80) //
             .setTargetDir(directoryFile.getAbsolutePath())
             .setCompressListener(new OnCompressListener() {
                 @Override
                 public void onStart() {
                     Log.d("Luban","start compress");
                 }

                 @Override
                 public void onSuccess(File file) {
                     Log.d("Luban","compress success");
                     loadBitmap(Uri.fromFile(file));
                 }

                 @Override
                 public void onError(Throwable e) {
                     Log.d("Luban","compress error");
                     loadBitmap(uri);
                 }
             }).launch();
    }

    @NonNull
    @Override
    public Bitmap process(Bitmap raw) {
        //Gray level
        int width = raw.getWidth();
        int height = raw.getHeight();
        Bitmap bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(raw, 0, 0, paint);
        return bmpGray;
    }
}
