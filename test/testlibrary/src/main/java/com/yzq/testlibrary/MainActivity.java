package com.yzq.testlibrary;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //这里是启动第二个activity
        findViewById(R.id.mBtnLoadPlugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });
        findViewById(R.id.mBtnStartProxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProxy();
            }


        });
    }
    private void loadPlugin() {
        HookManager.getInstance().loadPlugin(this);
        Toast.makeText(this, "加载完成", Toast.LENGTH_LONG).show();
    }

    private void startProxy() {
        Intent intent = new Intent(this, ProxyActivity.class);//这里就是一个占坑的activity
        //这里是拿到我们加载的插件的第一个activity的全类名
        intent.putExtra("ClassName", HookManager.getInstance().packageInfo.activities[0].name);
        startActivity(intent);
    }
}
