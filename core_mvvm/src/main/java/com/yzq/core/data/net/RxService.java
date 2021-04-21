package com.yzq.core.data.net;

import com.yzq.core.CoreApp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by hpw on 16/11/2.
 */

public class RxService {
    private static final int TIMEOUT_READ = 120;
    private static final int TIMEOUT_CONNECTION = 120;
  //  private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //设置Cache
            .addNetworkInterceptor(cacheInterceptor)//缓存方面需要加入这个拦截器
            .addInterceptor(new LogInterceptor())
            .cache(HttpCache.getCache())
            //.addInterceptor(new EnhancedCacheInterceptor())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            //打印日志
            .build();
    private static OkHttpClient okHttpClientNotAddParam = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //设置Cache
            .addNetworkInterceptor(cacheInterceptor)//缓存方面需要加入这个拦截器
            .cache(HttpCache.getCache())
            .addInterceptor(new EnhancedCacheInterceptor())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            //打印日志
            //  .addInterceptor(interceptor)
            .build();
    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, CoreApp.getInstance().setBaseUrl());
    }
    public static <T> T createNotAddParam(Class<T> clazz) {

        return createApiNotAddParam(clazz, CoreApp.getInstance().setBaseUrl());
    }
    public static <T> T createApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomizeGsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
    public static <T> T createApiNotAddParam(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClientNotAddParam)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomizeGsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}

