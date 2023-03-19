package com.qring.common.test.common.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/24 15:48
 * @Version 1.0
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Value("")
public @interface Foo {

    @AliasFor(
            annotation = Value.class
    )
    String value();

    String name() default "";

    String cron() default "";
}
