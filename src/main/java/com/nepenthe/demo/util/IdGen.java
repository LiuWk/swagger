package com.nepenthe.demo.util;

/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @author iuling
 * @version 2016-01-15
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }


    @Override
    public UUID generateId() {
        return null;
    }

    public static void main(String[] args) {

    }


}
