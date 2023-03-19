package com.qring.common.base.result;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qring
 * @Description 统一结果返回
 * @Date 2022/7/4 16:34
 * @Version 1.0
 */
@Data
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = -3430603013914181393L;
    protected T data;
    private Integer code;
    private String msg;
    private Boolean success;

    public ResultDTO() {
        init(CommonResultCode.SUCCESS.getCode(), null, null);
    }

    public ResultDTO(ResultCode resultCode, String msg) {
        init(resultCode.getCode(), msg, null);
    }

    public ResultDTO(ResultCode resultCode, T data) {
        init(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public ResultDTO(ResultCode resultCode, T data, String msg) {
        init(resultCode.getCode(), msg, data);
    }

    public ResultDTO(Integer code, T data, String msg) {
        init(code, msg, data);
    }

    private ResultDTO(ResultCode resultCode) {
        init(resultCode.getCode(), resultCode.getMsg(), null);
    }

    private ResultDTO(Integer resultCode, String msg) {
        init(resultCode, msg, null);
    }

    public ResultDTO(ResultDTO<T> resultDTO) {
        init(resultDTO.getCode(), resultDTO.getMsg(), null);
    }

    public static <T> ResultDTO<T> success() {
        return new ResultDTO<>(CommonResultCode.SUCCESS);
    }

    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<>(CommonResultCode.SUCCESS, data);
    }

    public static <T> ResultDTO<T> success(T data, String msg) {
        return new ResultDTO<>(CommonResultCode.SUCCESS, data, msg);
    }

    public static <T> ResultDTO<T> fail() {
        return new ResultDTO<>(CommonResultCode.SYSTEM_ERROR);
    }

    public static <T> ResultDTO<T> fail(String msg) {
        return new ResultDTO<>(CommonResultCode.SYSTEM_ERROR.getCode(), msg);
    }

    public static <T> ResultDTO<T> fail(Integer code, String msg) {
        return new ResultDTO<>(code, msg);
    }

    public static <T> ResultDTO<T> fail(Integer code, T data, String msg) {
        return new ResultDTO<>(code, data, msg);
    }

    private void init(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = ObjectUtil.equal(this.code, CommonResultCode.SUCCESS.getCode()) ? Boolean.TRUE : Boolean.FALSE;
    }
}
