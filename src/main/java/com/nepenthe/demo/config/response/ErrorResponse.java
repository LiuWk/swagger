package com.nepenthe.demo.config.response;

import com.nepenthe.demo.util.Code;

/**
 * 异常时的返回报文
 *
 * @author lwk
 * @date 2019-05-06 16:33
 */
public class ErrorResponse extends Response {
    /**
     * 成功true，失败false
     */
    private Boolean success;

    public ErrorResponse(Code code, String msg) {
        super(code, msg);
        this.success = false;
    }

    public ErrorResponse(Code code, String msg, Object e) {
        super(code, msg, e);
        this.success = false;
    }

    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
