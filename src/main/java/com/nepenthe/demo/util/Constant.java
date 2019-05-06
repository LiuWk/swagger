package com.nepenthe.demo.util;

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
}
