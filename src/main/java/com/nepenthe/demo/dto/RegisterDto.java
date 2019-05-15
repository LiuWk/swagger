package com.nepenthe.demo.dto;


import java.io.Serializable;

/**
 * 注册用户请求参数
 *
 * @author lwk
 */
public class RegisterDto implements Serializable {
    private String name;
    private Integer gender;
    private String mobile;
    private String password;
    /**
     * 验证码
     */
    private String securityCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
