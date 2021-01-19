package com.yzq.plugin;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.yzq.testlibrary.IMyAidlInterface;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  mBinder;
    }


    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int test(int x, int y) {
            return x+y;
        }
    };
}
