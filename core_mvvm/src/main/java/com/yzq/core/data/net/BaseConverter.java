package com.yzq.core.data.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

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
 class BaseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    BaseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String jsonString = value.string();
        try {
            JSONObject object = new JSONObject(jsonString);
            int code = object.getInt("code");
            String message=object.getString("message");
            if (code==200||code==666||code==420){

            }else {
                jsonString="{ \"code\": "+code+", \"message\": \""+message+"\" }";
                throw new CoreApiException(code,message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            value.close();
        }
        return adapter.fromJson(jsonString);
    }

}
