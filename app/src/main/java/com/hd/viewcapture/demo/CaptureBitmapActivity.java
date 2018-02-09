package com.hd.viewcapture.demo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class CaptureBitmapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_bitmap);
        setActionBarTitle("Capture Bitmap");
        Uri uri = getIntent().getBundleExtra("bundle").getParcelable("uri");
        loadBitmap(uri, (ImageView) findViewById(R.id.captureImage));
    }
}
