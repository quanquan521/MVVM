package com.yzq.core;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 *
 */

public class RxManager {

    public RxBus mRxBus = RxBus.$();
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察源
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//


    public void on(String eventName, Consumer<Object> action1) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        mCompositeDisposable.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace();
                    }
                }));
    }

    public void add(Disposable m) {
        mCompositeDisposable.add(m);
    }

    public void clear() {
        mCompositeDisposable.clear();// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet())
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}