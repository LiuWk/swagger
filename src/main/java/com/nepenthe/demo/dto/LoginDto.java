package com.nepenthe.demo.dto;

import java.io.Serializable;

/**
 * 登录返回dto
 * @author lwk
 * @date 2019-05-13 12:05
 */
public class LoginDto implements Serializable {
    private String token;
    private Integer userId;
    private String mobile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
