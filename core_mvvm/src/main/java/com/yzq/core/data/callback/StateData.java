package com.yzq.core.data.callback;

import androidx.annotation.NonNull;

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
public class StateData <T>{
    private Status status;
    private Throwable error;
    private T data;

    public StateData<T> success(@NonNull T data) {
        this.status=Status.OK;
        this.data = data;
        this.error = null;
        return this;
    }

    public StateData<T> error(@NonNull Throwable error) {
        this.status=Status.ERROR;
        this.data = null;
        this.error = error;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
