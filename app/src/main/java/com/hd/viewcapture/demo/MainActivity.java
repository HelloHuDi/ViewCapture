package com.hd.viewcapture.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;


/**
 * Created by hd on 2018/2/6 .
 * demo
 */
public class MainActivity extends AppCompatActivity implements CaptureManager.OnSaveResultListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void capture(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.captureView:
                captureView(findViewById(R.id.lin));
                return;
            case R.id.captureScrollView:
                intent = new Intent(this, ScrollViewActivity.class);
                break;
            case R.id.captureHorizontalScrollView:
                intent = new Intent(this, HorizontalScrollViewActivity.class);
                break;
            case R.id.captureListView:
                intent = new Intent(this, ListViewActivity.class);
                break;
            case R.id.captureRecyclerView:
                intent = new Intent(this, RecyclerViewActivity.class);
                break;
            case R.id.captureWebView:
                intent = new Intent(this, WebViewActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void captureView(View view) {
        ViewCapture.with(view)//
                   .asPNG()//
                   .setFileName("viewCapture")//
                   .setDirectoryPath("viewCaptureFile")//
                   .setOnSaveResultListener(this)//
                   .save();
    }

    @Override
    public void onSaveResult(boolean isSaved, String path, Uri uri) {
        Log.d("tag", "onSaveResult ：" + isSaved + "=" + path + "=" + uri);
    }
}
