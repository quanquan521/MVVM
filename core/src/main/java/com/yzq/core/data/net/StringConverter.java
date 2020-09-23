package com.yzq.core.data.net;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2020/3/17.
 * <p>
 * 描述：
 */

public class StringConverter implements JsonDeserializer{

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Log.i("quanquanquan",json.getAsJsonPrimitive().getAsString()+"****");
        return json.getAsJsonPrimitive().getAsString();
    }


}

