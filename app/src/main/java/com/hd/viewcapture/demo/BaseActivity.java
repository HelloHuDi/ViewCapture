package com.hd.viewcapture.demo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * Created by hd on 2018/2/7 .
 * base
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void loadBitmap(Uri uri){
        loadBitmap(uri,(ImageView) findViewById(R.id.ivView));
    }

    protected void loadBitmap(Bitmap bitmap){
        loadBitmap(bitmap,(ImageView) findViewById(R.id.ivView));
    }

    protected <T>void loadBitmap(T t,ImageView imageView){
        GlideApp.with(this)//
                .asBitmap()//
                .load(t)//
                .placeholder(R.mipmap.icon)//
                .error(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.NONE)//
                .skipMemoryCache(true)//
                .into(imageView);
    }


}
