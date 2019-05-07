package com.nepenthe.demo.config.request;

/**
 * 请求报文公共字段
 * @author lwk
 * @date 2019-05-07 09:38
 */
public class Request {
    /**
     * 登录信息
     */
    private String token;

    /**
     * 参数
     */
    private Object body;

    /**
     *  服务器返回的毫秒值
     */
    private Long dateTimeMillis;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Long getDateTimeMillis() {
        return dateTimeMillis;
    }

    public void setDateTimeMillis(Long dateTimeMillis) {
        this.dateTimeMillis = dateTimeMillis;
    }
}
