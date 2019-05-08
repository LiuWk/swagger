package com.nepenthe.demo.config.response;

import com.nepenthe.demo.util.Code;

import java.io.Serializable;

/**
 * 返回报文
 *
 * @author lwk
 * @date 2019-05-06 15:52
 */
public class Response implements Serializable {
    /**
     * 成功true，失败false
     */
    private Boolean success;
    /**
     * 错误代码 0000成功
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 封装json的map
     */
    private Object body;
    /**
     * 返回一个当前 服务器时间毫秒值
     */
    private Long currentTimeMillis;

    public Response(Code code, String msg, Object body) {
        this.success = true;
        this.code = code.getCode();
        this.msg = msg;
        this.body = body;
        this.currentTimeMillis = System.currentTimeMillis();
    }

    public Response(Code code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
        this.currentTimeMillis = System.currentTimeMillis();
    }

    public Response() {

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
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }
}
