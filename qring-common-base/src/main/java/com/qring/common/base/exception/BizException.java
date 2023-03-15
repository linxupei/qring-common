package com.qring.common.base.exception;

import com.qring.common.base.result.CommonResultCode;
import com.qring.common.base.result.ResultCode;

/**
 * @Author Qring
 * @Description 非系统异常
 * @Date 2023/3/8 22:59
 * @Version 1.0
 */
public class BizException extends RuntimeException {

    private Integer code;

    private String[] args;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
        this.code = CommonResultCode.BUSINESS_ERROR.getCode();
    }

    public BizException(String message, String[] args) {
        super(message);
        this.code = CommonResultCode.BUSINESS_ERROR.getCode();
        this.args = args;
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public BizException(ResultCode resultCode, String[] args) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.args = args;
    }

    public BizException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = CommonResultCode.BUSINESS_ERROR.getCode();
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = CommonResultCode.BUSINESS_ERROR.getCode();
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = CommonResultCode.BUSINESS_ERROR.getCode();
    }

    public Integer getCode() {
        return this.code;
    }

    public String[] getArgs() {
        return this.args;
    }
}
