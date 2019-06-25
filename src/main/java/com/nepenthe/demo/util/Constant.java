package com.nepenthe.demo.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 常量
 *
 * @author lwk
 * @date 2019-05-06 16:25
 */
public class Constant {
    public static String SUCCESS = "success";
    public static String ERROR = "error";

    public static String getMsg(Code error) {
        for(Code code : Code.values()){
            if (code.getCode().equals(error.getCode())){
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
