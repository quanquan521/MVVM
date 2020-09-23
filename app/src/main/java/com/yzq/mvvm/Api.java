package com.yzq.mvvm;

import com.yzq.mvvm.bean.GankIoDataBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
public interface Api {
    @GET("v2/data/category/{category}/type/{type}/page/{page}/count/{count}")
    Observable<GankIoDataBean> getGankIoData(@Path("category") String category, @Path("type") String type, @Path("page") int page, @Path("count") int count);
}
