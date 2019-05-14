package com.nepenthe.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nepenthe.demo.dto.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 工具类
 *
 * @author lwk
 * @date 2019-05-14 15:37
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Request getRequest(String json) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return JSONObject.parseObject(json,Request.class);
        } catch (Exception e) {
            logger.error("getRequest exception={}",e);
        }
        return null;
    }
}
