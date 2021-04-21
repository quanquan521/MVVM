package com.yzq.core;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.yzq.core.di.component.AppComponent;
import com.yzq.core.di.component.DaggerAppComponent;
import com.yzq.core.di.module.AppModule;
import com.yzq.core.exception.Cockroach;
import com.yzq.core.exception.ExceptionHandler;

public abstract class CoreApp extends Application {
    private static CoreApp mApp;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    public static AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        getScreenSize();

    }

    public static synchronized CoreApp getInstance() {
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    public abstract String setBaseUrl();
    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            /*/如果module中带了参数，则编译期不会生成create()方法 DaggerAppComponent.create()*/
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mApp))
                    .build();
        }
        return appComponent;
    }
    public void install() {
        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
        Cockroach.install(this,new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
              //  com.yzq.mvpframe.utils.ToastUtils.showToast(getApplicationContext(),"模块加载异常！！");
            }

            @Override
            protected void onEnterSafeMode() {
              //  com.yzq.mvpframe.utils.ToastUtils.showToast(getApplicationContext(),"模块加载异常！！！");
            }
            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                Thread thread = Looper.getMainLooper().getThread();
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", e);
                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
            }
        });
    }


}
