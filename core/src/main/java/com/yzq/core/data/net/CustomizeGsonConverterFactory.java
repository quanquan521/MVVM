package com.yzq.core.data.net;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2019/6/13.
 * <p>
 * 描述：
 */
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author lqx
 * 自定义GsonConverterFactory
 */
public class CustomizeGsonConverterFactory extends Converter.Factory {
    public static CustomizeGsonConverterFactory create() {

       /* GsonBuilder builder=new GsonBuilder().serializeNulls();
        builder.registerTypeAdapter(String.class,new StringConverter());*/
        return create(new Gson());
    }

    public static CustomizeGsonConverterFactory create(Gson gson) {
        return new CustomizeGsonConverterFactory(gson);
    }

    private final Gson gson;

    private CustomizeGsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));

        return new BaseConverter(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));


        return new BaseConverter(gson, adapter);
    }

}