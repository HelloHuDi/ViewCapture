package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewActivity extends BaseCaptureActivity<RecyclerView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
    }
}
