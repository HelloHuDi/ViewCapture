package com.hd.viewcapture.demo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class NewApiCaptureActivity extends BaseCaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_api_capture);
        setActionBarTitle("MediaProjection Capture");
    }

    public void capture(View view) {
        captureView(this);
    }

}
