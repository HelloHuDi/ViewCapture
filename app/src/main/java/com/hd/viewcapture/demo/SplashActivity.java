package com.hd.viewcapture.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hd.splashscreen.SimpleConfig;
import com.hd.splashscreen.SimpleSplashFinishCallback;
import com.hd.splashscreen.SimpleSplashScreen;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by hd on 2018/2/6 .
 * request permission
 */
public class SplashActivity extends AppCompatActivity implements SimpleSplashFinishCallback,//
        EasyPermissions.PermissionCallbacks {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        SimpleSplashScreen screen = findViewById(R.id.screen);
        SimpleConfig simpleConfig = new SimpleConfig();
        simpleConfig.setTextSize(25f);
        simpleConfig.setText("VIEWCAPTURE");
        simpleConfig.setIconId(R.mipmap.icon);
        simpleConfig.setIconDelayTime(1500);
        simpleConfig.setTextColor(R.color.colorAccent);
        simpleConfig.setCallback(this);
        screen.addConfig(simpleConfig);
        screen.start();
    }

    @Override
    public void loadFinish() {
        if (!isDestroyed()) {
            requestPermission();
        }
    }

    private final int RESULT_CODE = 100;

    private final String par = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, par)) {
            goMain();
        } else {
            EasyPermissions.requestPermissions(this, "You need to save the screenshot to the local", RESULT_CODE, par);
        }
    }

    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RESULT_CODE) {
            goMain();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (EasyPermissions.hasPermissions(this, par)) {
            goMain();
        }
    }
}
