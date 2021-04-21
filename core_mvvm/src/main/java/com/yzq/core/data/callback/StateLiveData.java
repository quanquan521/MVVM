package com.yzq.core.data.callback;

import androidx.lifecycle.MutableLiveData;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2021/4/9.
 * <p>
 * 描述：
 */
public class StateLiveData<T> extends MutableLiveData<StateData<T>> {
    public void postError(Throwable throwable) {
        postValue(new StateData<T>().error(throwable));
    }
    public void postSuccess(T data) {
        postValue(new StateData<T>().success(data));
    }
}
