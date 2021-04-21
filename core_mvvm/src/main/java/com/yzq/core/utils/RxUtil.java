package com.yzq.core.utils;

import com.yzq.core.data.entity.CoreDataResponse;
import com.yzq.core.data.net.CoreApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    /**
     * 线程调度
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    /**
     * 统一返回结果处理   com.yzq.mvvm.bean  实体类是gsonformat  生成    要只复制data 里面的    重构时处理
     *
     * 需要统一格式  （暂留）
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<CoreDataResponse<T>, T> handleResult() {   //compose判断结果
        return new ObservableTransformer<CoreDataResponse<T>, T>() {
            @Override
            public Observable<T> apply(Observable<CoreDataResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreDataResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(CoreDataResponse<T> tCoreDataResponse) throws Exception {
                        if (tCoreDataResponse.getCode() == 200||tCoreDataResponse.getCode() == 666||tCoreDataResponse.getCode() == 420) {
                            return createData(tCoreDataResponse.getData());
                        }else {
                            return Observable.error(new CoreApiException(tCoreDataResponse.getCode(),tCoreDataResponse.getMsg()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e){
                try {
                    e.onNext(t);
                    e.onComplete();
                } catch (Exception e1) {
                    e.onError(e1);
                }
            }
        });
    }
}
