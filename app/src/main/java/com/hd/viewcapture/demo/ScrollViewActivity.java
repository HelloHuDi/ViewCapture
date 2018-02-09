package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewActivity extends BaseCaptureActivity<ScrollView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        setActionBarTitle("ScrollView Capture");
    }

    public void capture(View view) {
        captureView((ScrollView) findViewById(R.id.scrollView));
    }
}
