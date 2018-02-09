package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Arrays;

public class ListViewActivity extends BaseCaptureActivity<ListView> {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        setActionBarTitle("ListView Capture");
        listView = findViewById(R.id.listView);
        CommonAdapter commonAdapter=new CommonAdapter<String>(this, R.layout.layout_item, Arrays.asList(getResources().getStringArray(R.array.listView_item))) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView textView = viewHolder.getView(R.id.tv);
                textView.setText(item);
            }
        };
        listView.setAdapter(commonAdapter);
        listView.addFooterView(LayoutInflater.from(this).inflate(R.layout.layout_add_item, null));
    }

    public void capture(View view) {
        captureView(listView);
    }

}
