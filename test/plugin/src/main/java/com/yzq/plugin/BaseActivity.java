package com.yzq.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yzq.testlibrary.ProxyActivityInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2021/1/7.
 * <p>
 * 描述：
 */
public class BaseActivity extends AppCompatActivity implements ProxyActivityInterface {
    public Activity that;

    @Override
    public void attach(Activity proxyActivity) {
        that=proxyActivity;
    }

    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }
    @Override
    public void setContentView(int layoutResID) {
        that.setContentView(layoutResID);
    }
    @Override
    public View findViewById(int id) {
        return that.findViewById(id);
    }
    @Override
    public Intent getIntent() {
        if (that != null) {
            return that.getIntent();
        }
        return super.getIntent();
    }
    @Override
    public ClassLoader getClassLoader() {
        return that.getClassLoader();
    }
    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return that.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return that.getApplicationInfo();
    }

    @Override
    public Window getWindow() {
        return that.getWindow();
    }


    @Override
    public WindowManager getWindowManager() {
        return that.getWindowManager();
    }
    @Override
    public void startActivity(Intent intent) {
//        ProxyActivity --->className
        Intent m = new Intent();
        m.putExtra("ClassName", intent.getComponent().getClassName());
        that.startActivity(m);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
