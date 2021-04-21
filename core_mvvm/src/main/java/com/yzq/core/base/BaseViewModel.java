package com.yzq.core.base;

import android.app.Application;

import com.yzq.core.RxManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


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
