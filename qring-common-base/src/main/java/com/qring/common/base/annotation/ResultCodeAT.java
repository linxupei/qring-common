package com.qring.common.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Qring
 * @Description 返回编码
 * @Date 2023/3/18 21:12
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultCodeAT {

    /**
     * @return 编码起始值
     */
    int start();

    /**
     * @return 编码结束值
     */
    int end();
}
