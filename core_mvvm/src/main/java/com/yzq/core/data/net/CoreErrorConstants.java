package com.yzq.core.data.net;

import java.util.HashMap;
import java.util.Map;

//本地错误map


public class CoreErrorConstants {
    public static Map<Integer, String> errors;

    static {
        errors = new HashMap<>();
        errors.put(886,"登录超时");

    }
}
