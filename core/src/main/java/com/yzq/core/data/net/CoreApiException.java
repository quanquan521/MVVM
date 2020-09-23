package com.yzq.core.data.net;

import java.io.IOException;

/**
 *
 * //自定义异常类
 *
 */
public class CoreApiException extends IOException {
    private  int code;
    private  String msg;
    public CoreApiException(int code,String msg) {
        super(msg);
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
