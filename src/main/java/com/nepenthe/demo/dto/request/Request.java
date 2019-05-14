package com.nepenthe.demo.dto.request;

import com.alibaba.fastjson.JSONObject;

/**
 * 请求报文公共字段
 * @author lwk
 * @date 2019-05-07 09:38
 */
public class Request {
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 登录信息
     */
    private String token;

    /**
     * 参数
     */
    private JSONObject body;

    /**
     *  服务器返回的毫秒值
     */
    private Long dateTimeMillis;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public Long getDateTimeMillis() {
        return dateTimeMillis;
    }

    public void setDateTimeMillis(Long dateTimeMillis) {
        this.dateTimeMillis = dateTimeMillis;
    }
}
