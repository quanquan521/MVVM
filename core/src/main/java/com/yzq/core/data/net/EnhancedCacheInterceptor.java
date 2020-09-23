package com.yzq.core.data.net;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2019/8/21.
 * <p>
 * 描述：
 */
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created on 2016/11/30.
 *
 * @author WangYi
 */

public class EnhancedCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (request.method().equals("POST")) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(buffer.readString(charset));
            buffer.close();
        }
        ResponseBody responseBody = response.body();
        MediaType contentType = responseBody.contentType();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String key = sb.toString();
        String json = buffer.clone().readString(charset);
        //Log.d(CacheManager.TAG, "EnhancedCacheInterceptor -> key:" +json);
        return chain.proceed(request);
    }
}