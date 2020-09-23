package com.yzq.mvvm.viewmodel;

import android.app.Application;

import com.yzq.core.base.BaseViewModel;
import com.yzq.core.data.net.RxService;
import com.yzq.core.utils.RxUtil;
import com.yzq.mvvm.Api;
import com.yzq.mvvm.bean.GankIoDataBean;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2020/9/16.
 * <p>
 * 描述：
 */
public class MainViewModel extends BaseViewModel {
   private MutableLiveData<GankIoDataBean> gankIoDataBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GankIoDataBean> getGankIoDataBeanMutableLiveData() {
        return gankIoDataBeanMutableLiveData;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
   public LiveData<GankIoDataBean> getData(int page){
        mRxManager.add(RxService.createApi(Api.class).getGankIoData("Girl", "Girl",page, 10).compose(RxUtil.<GankIoDataBean>rxSchedulerHelper()).subscribe(new Consumer<GankIoDataBean>() {
            @Override
            public void accept(GankIoDataBean gankIoDataBean) throws Exception {
                gankIoDataBeanMutableLiveData.postValue(gankIoDataBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                gankIoDataBeanMutableLiveData.postValue(null);
            }
        }));
        return gankIoDataBeanMutableLiveData;
    }
}
