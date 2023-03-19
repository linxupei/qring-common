package com.qring.common.base.result;

import com.qring.common.base.annotation.ResultCodeAT;

/**
 * @Author Qring
 * @Description 公共结果编码
 * @Date 2023/3/12 22:36
 * @Version 1.0
 */
@ResultCodeAT(start = 100, end = 999)
public class CommonResultCode extends ResultCode {

    public static final CommonResultCode SUCCESS = new CommonResultCode(100, "Success");
    public static final CommonResultCode SYSTEM_ERROR = new CommonResultCode(101, "common.system.error");
    public static final CommonResultCode BUSINESS_ERROR = new CommonResultCode(102, "common.business.error");
    public static final CommonResultCode ERROR_PARAM = new CommonResultCode(103, "common.param.error");
    public static final CommonResultCode REQUEST_METHOD_ERROR = new CommonResultCode(104, "common.method.error");
    public static final CommonResultCode ILLEGAL_PARAM = new CommonResultCode(105, "common.illegal.error");

    public CommonResultCode(Integer code, String msg) {
        super(code, msg);
    }
}
