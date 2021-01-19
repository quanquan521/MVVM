package com.yzq.testlibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

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
public interface ProxyActivityInterface {
    //生命周期的activity

    public void attach(Activity proxyActivity);


    public void onCreate(Bundle savedInstanceState);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public boolean onTouchEvent(MotionEvent event);

    public void onBackPressed();
}
