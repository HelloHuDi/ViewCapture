package com.hd.viewcapture.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;


/**
 * Created by hd on 2018/2/6 .
 *
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.tv);
        ViewCapture.with(textView).asHide().setOnSaveResultListener(new CaptureManager.OnSaveResultListener() {
            @Override
            public void onSaveResult(boolean isSaved, String path, Uri uri) {
                Log.d("tag","onSaveResult ："+isSaved+"="+path+"="+uri);
            }
        }).save();
    }
}
