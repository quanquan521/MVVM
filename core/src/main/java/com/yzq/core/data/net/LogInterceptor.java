package com.yzq.core.data.net;

import android.util.Log;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2019/5/31.
 * <p>
 * 描述：
 */
public class LogInterceptor implements Interceptor {
    public static String TAG = "LogInterceptor";

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuild;
        String method = oldRequest.method();
        String postBodyString="";
        if("POST".equals(method)){
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            //formBodyBuilder.add("device_token", SpUtil.getDefaultDevicetocken());
           // formBodyBuilder.add("version","201805311016");
            newRequestBuild = oldRequest.newBuilder();

            RequestBody formBody = formBodyBuilder.build();
            postBodyString = bodyToString(oldRequest.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
        }else {
            // 添加新的参数
            HttpUrl.Builder commonParamsUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
                  //  .addQueryParameter("device_token", SpUtil.getDefaultDevicetocken())
                  //  .addQueryParameter("version","201805311016");

            newRequestBuild = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(commonParamsUrlBuilder.build());
        }
        Request newRequest = newRequestBuild
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "zh")
                .build();

        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(newRequest);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        int httpStatus = response.code();
        StringBuilder logSB = new StringBuilder();
        logSB.append("-------start:"+method+"|");
        logSB.append(newRequest.toString()+"\n|");
        logSB.append(method.equalsIgnoreCase("POST")?"post参数{"+ postBodyString +"}\n|":"");
        logSB.append("httpCode=" + httpStatus + ";Response:" + content+"\n|");
        logSB.append("----------End:" + duration + "毫秒----------");
        Log.d(TAG,logSB.toString());
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
