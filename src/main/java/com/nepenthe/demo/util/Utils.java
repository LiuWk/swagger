package com.nepenthe.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.nepenthe.demo.dto.request.Request;
import com.nepenthe.demo.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.poi.hssf.model.InternalSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 工具类
 *
 * @author lwk
 * @date 2019-05-14 15:37
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * 转换json为请求对象
     *
     * @param json rest接口请求数据
     * @return
     */
    public static Request getRequest(String json) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return JSONObject.parseObject(json, Request.class);
        } catch (Exception e) {
            logger.error("getRequest exception={}", e.getMessage(),e);
        }
        return null;
    }

    /**
     * md5 数据
     *
     * @param content 内容
     * @param salt    混淆参数
     * @return 返回加密之后的字符
     */
    public static String md5(String content, String salt) {
        return DigestUtils.md5Hex(String.format("%s_%s", content, salt));
    }

    /**
     * 验证手机号格式
     *
     * @return true 格式正确 false 格式不正确
     */
    public static boolean checkMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return Constant.MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * 判断是否有空值，不要使用！操作
     *
     * @param objects
     * @return true至少有一个为空，false都不为空
     */
    public static boolean hasEmpty(Object... objects) {
        for (Object obj : objects) {
            if (obj == null || "".equals(obj)) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(md5("123456", "15211110000"));
        System.out.println(hasEmpty(null, ""));
    }
}
