package com.qring.common.base.exception;

import com.qring.common.base.result.CommonResultCode;

/**
 * @Author Qring
 * @Description 系统异常
 * @Date 2023/3/15 21:56
 * @Version 1.0
 */
public class SysException extends RuntimeException {
    private Integer code;

    public SysException() {
    }

    public SysException(String message) {
        super(message);
        this.code = CommonResultCode.SYSTEM_ERROR.getCode();
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
        this.code = CommonResultCode.SYSTEM_ERROR.getCode();
    }

    public SysException(Throwable cause) {
        super(cause);
        this.code = CommonResultCode.SYSTEM_ERROR.getCode();
    }

    public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = CommonResultCode.SYSTEM_ERROR.getCode();
    }

    public Integer getCode() {
        return this.code;
    }
}
