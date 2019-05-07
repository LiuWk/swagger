package com.nepenthe.demo.util;

/**
 * 错误码相关
 * @author lwk
 * @date 2019-05-06 17:15
 */
public enum Code {
    /**
     * 错误码相关
     */
    SUCCESS("0000", "操作成功"),
    SYSTEM_ERROR("-9999", "系统异常"),
    PARAMETER_IS_NULL("-0001", "参数为空"),
    ;

    private String code;
    private String msg;

    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
