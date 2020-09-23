package com.yzq.core.base;

import android.app.Application;

import com.yzq.core.RxManager;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author jingbin
 * @data 2018/5/28
 */
public class BaseViewModel extends AndroidViewModel {
    public RxManager mRxManager = new RxManager();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRxManager.clear();
    }
}
