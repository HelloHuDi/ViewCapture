package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.view.View;

public class HorizontalScrollViewActivity extends BaseCaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);
        setActionBarTitle("HorizontalScrollView Capture");
    }

    public void capture(View view) {
        captureView(findViewById(R.id.horizontalScrollView));
    }
}
