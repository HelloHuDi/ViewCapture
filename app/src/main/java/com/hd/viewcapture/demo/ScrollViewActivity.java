package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.view.View;

public class ScrollViewActivity extends BaseCaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        setActionBarTitle("ScrollView Capture");
    }

    public void capture(View view) {
        captureView(findViewById(R.id.scrollView));
    }
}
