package com.qring.common.base.result;

import lombok.Data;

/**
 * @Author Qring
 * @Description 统一结果返回
 * @Date 2022/7/4 16:34
 * @Version 1.0
 */
@Data
public class ResultData<T> {

    private int status;
    private String message;
    private T data;
    private long timestamp ;


    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ResultCode.RC100.getCode());
        resultData.setMessage(ResultCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

}
