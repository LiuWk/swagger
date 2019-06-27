package com.nepenthe.demo.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.*;
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
        // 范围精确到周
        Period period = new Period(DateTime.parse("19170611", DateTimeFormat.forPattern("yyyyMMdd")).toLocalDate(), DateTime.now().toLocalDate(), PeriodType.yearWeekDay());
        System.out.println(period.getYears());
        Map<String,String> hmap = new HashMap<>();
        hmap.put("registerPlatform","111");
        if (!"1206".equals(hmap.get("registerPlatform")) && !"11".equals(hmap.get("registerPlatform"))) {
            System.out.println("1212");
        }

        try {
            // 执行脚本，命令
            Runtime.getRuntime().exec("notepad.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
