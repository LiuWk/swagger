package com.nepenthe.demo.config.interceptor;

import com.nepenthe.demo.util.Code;

/**
 * @author lwk
 * @date 2019-05-08 14:14
 */
public class BaseServiceException extends RuntimeException {

    private Code code;
    private String msg;

    BaseServiceException() {

    }

    /**
     * 自定义异常
     *
     * @param code 错误码
     * @param msg  自定义提示
     * @param e    接收到的异常
     */
    BaseServiceException(Code code, String msg, String e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
