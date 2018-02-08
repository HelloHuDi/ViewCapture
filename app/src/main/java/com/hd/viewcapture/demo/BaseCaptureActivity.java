package com.hd.viewcapture.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;


/**
 * Created by hd on 2018/2/7 .
 * base capture
 */
public abstract class BaseCaptureActivity<V extends View> extends BaseActivity implements CaptureManager.OnSaveResultListener {

    private boolean save_to_file = true;

    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if(uri!=null) {
            Intent intent = new Intent(this, CaptureBitmapActivity.class);
            Bundle bundle=new Bundle();
            bundle.putParcelable("uri", uri);
            intent.putExtra("bundle",bundle);
            startActivity(intent);
        }
    }

    protected void captureView(V v) {
        if (save_to_file) {
            ViewCapture.with(v)//
                       .asJPG(80)//
                       .setFileName("viewCapture")//
                       .setDirectoryName("viewCaptureFile")//
                       .setOnSaveResultListener(this)//
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
        loadBitmap(uri);
    }
}
