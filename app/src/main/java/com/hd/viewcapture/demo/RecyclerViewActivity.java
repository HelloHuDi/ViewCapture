package com.hd.viewcapture.demo;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;

public class RecyclerViewActivity extends BaseCaptureActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setActionBarTitle("RecyclerView Capture");
        recyclerView = findViewById(R.id.recyclerView);
        setRecycleView(0);
    }

    private void setRecycleView(int type) {
        switch (type) {
            case 0:
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                break;
            case 1:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case 2:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.layout_item, //
                  Arrays.asList(getResources().getStringArray(R.array.listView_item))) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                ((TextView) holder.getView(R.id.tv)).setText(s);
            }
        });
    }

    public void capture(View view) {
        captureView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_recycleview_manger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linearLayout:
                setRecycleView(0);
                break;
            case R.id.gridLayout:
                setRecycleView(1);
                break;
            case R.id.staggeredGridLayout:
                setRecycleView(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
