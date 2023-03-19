package com.qring.common.base.result;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.qring.common.base.annotation.ResultCodeAT;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Qring
 * @Description 返回状态码
 * @Date 2022/7/4 16:36
 * @Version 1.0
 */
@Data
public abstract class ResultCode {

    private int code;

    private String msg;

    private ResultCode() {
    }

    protected ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        ResponseCodeContainer.put(this);
    }

    public boolean isSuccess() {
        return CommonResultCode.SUCCESS.getCode() == this.code;
    }

    private static class ResponseCodeContainer {

        private static final Map<Integer, ResultCode> RESPONSE_CODE_MAP = new HashMap<>();

        private static final Map<Class<? extends ResultCode>, int[]> RESPONSE_CODE_RANGE_MAP = new HashMap<>();

        private static void register(Class<? extends ResultCode> clazz) {
            if (RESPONSE_CODE_RANGE_MAP.containsKey(clazz)) {
                return;
            }
            ResultCodeAT annotation = clazz.getAnnotation(ResultCodeAT.class);
            Assert.notNull(annotation,
                    StrUtil.format("<Result Code> Class:{} must be annotation with @ResultCodeAT", clazz.getSimpleName()));
            int start = annotation.start();
            int end = annotation.end();
            Assert.isTrue(start < end,
                    StrUtil.format("<Result Code> Class:{} start code must be less than end code!", clazz.getSimpleName()));
            RESPONSE_CODE_RANGE_MAP.forEach((k, v) -> {
                boolean judge = (start >= v[0] && start <= v[1]) || (end >= v[0] && end <= v[1]);
                Assert.isFalse(judge,
                        StrUtil.format("<Result Code> Class:{}'s id rang[{},{}] has intersection with class:{}"
                                , clazz.getSimpleName(), start, end, k.getSimpleName()));
            });
            RESPONSE_CODE_RANGE_MAP.put(clazz, new int[]{start, end});
        }

        private static void put(ResultCode resultCode) {
            register(resultCode.getClass());
            int code = resultCode.getCode();
            String className = resultCode.getClass().getSimpleName();
            int[] codeRange = RESPONSE_CODE_RANGE_MAP.get(resultCode.getClass());
            Assert.notNull(codeRange, StrUtil.format("<Result Code> Class:{} has not been registered!", className));
            Assert.checkBetween(code, codeRange[0], codeRange[1],
                    StrUtil.format("<Result Code> Class:{}-Id({}) out of range[{}, {}]!", className, code, codeRange[0], codeRange[1]));
            Assert.isFalse(RESPONSE_CODE_MAP.containsKey(code), "<Result Code> Class:{}-Id({}) is repeat!");
            RESPONSE_CODE_MAP.put(code, resultCode);
        }
    }
}
