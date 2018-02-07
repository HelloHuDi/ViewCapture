package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;

public class HorizontalScrollViewActivity extends BaseCaptureActivity<HorizontalScrollView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);
    }

    public void capture(View view) {
        captureView((HorizontalScrollView) findViewById(R.id.horizontalScrollView));
    }
}
