package com.qring.common.test.common.config;

import java.lang.annotation.*;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/7/10 19:50
 * @Version 1.0
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {
    /**
     * 枚举类型的class
     * 取值：getValue, getCode, getStatus, name
     * 描述：getDesc
     *
     * @return 字典类型
     */
    Class<? extends Enum<?>> enumType() default Void.class;

    /**
     * 默认值，获取不到字典则使用默认值
     *
     * @return ""
     */
    String defaultValue() default "";

    enum Void {}
}

