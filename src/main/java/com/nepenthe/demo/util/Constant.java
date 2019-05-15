package com.nepenthe.demo.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 常量
 *
 * @author lwk
 * @date 2019-05-06 16:25
 */
public class Constant {
    public static final Pattern MOBILE_PATTERN = Pattern.compile("^[1]([3-9])[0-9]{9}$");
    public static final long WEEK_TIME = 3600 * 24 * 7;
    public static final long MONTH_TIME = 3600 * 24 * 7 * 30;

    //##################### redis 相关key ############################
    public static final String USER_TOKEN = "user:token:uid_%s";
    public static final String USER_LOGIN_LOCK = "user:login:lock:mobile_%s";
    public static final String USER_INFO = "user:info:mobile_%s";

    public static final String CUSTOMER_INFO = "customer:info:cid_%s";
    public static final String CUSTOMER_INFO_LOCK = "customer:info:lock:token_%s";


    //################################################################

    public static String getMsg(Code error) {
        for (Code code : Code.values()) {
            if (code.getCode().equals(error.getCode())) {
                return code.getMsg();
            }
        }
        return "";
    }



}
