package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.widget.ListView;

public class ListViewActivity extends BaseCaptureActivity<ListView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
    }
}
