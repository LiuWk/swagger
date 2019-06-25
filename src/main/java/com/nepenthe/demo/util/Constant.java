package com.nepenthe.demo.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public static final String USER_REGISTER_LOCK = "user:register:lock:mobile_%s";
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


    public static void main(String[] args) {
        System.out.println();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(9);
        list.add(12);
        list.add(10);

        try {
            assert 1==11;
        } catch (Exception e) {
            e.printStackTrace();
        }

        list.stream().sorted();
        System.out.println(list);
        System.out.println(new StringBuilder("300000071").reverse().toString());

        //今天凌晨
        Date endTime = LocalDateTime.parse("2019-06-11", DateTimeFormat.forPattern("yyyy-MM-dd")).toDate() ;
        //昨日凌晨
        Date startTime = LocalDateTime.parse("2019-06-09", DateTimeFormat.forPattern("yyyy-MM-dd")).toDate() ;
        System.out.println(endTime);
        System.out.println(startTime);
        System.out.println(LocalDateTime.parse("2019-06-11", DateTimeFormat.forPattern("yyyy-MM-dd")).toDate() );


        //今天凌晨
        Date end = new DateTime().withTimeAtStartOfDay().toDate();
        //昨日凌晨
        Date start = new DateTime().plusDays(-1).withTimeAtStartOfDay().toDate();
        System.out.println(end);
        System.out.println(start);
    }
}
