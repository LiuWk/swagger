package com.nepenthe.demo.util;

import org.springframework.util.StringUtils;

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
        for (Code code : Code.values()) {
            if (code.getCode().equals(error.getCode())) {
                return code.getMsg();
            }
        }
        return "";
    }

    /**
     * 判断是否有空值
     *
     * @param objects
     * @return true至少有一个为空，false都不为空
     */
    public static boolean hasEmpty(Object... objects) {
        for (Object obj : objects) {
            if (StringUtils.isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

}
