package com.qring.common.base.annotation.qring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Qring
 * @Description Qring
 * @Date 2022/8/8 16:50
 * @Version 1.0
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Qring {
    String name() default "";
}
