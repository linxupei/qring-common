package com.qring.common.test.repository.model.t;

import com.qring.common.test.annotation.Foo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/24 15:49
 * @Version 1.0
 */
public class Bar {

    @Foo("fff")
    private String val;


    public static void main(String... args) throws NoSuchFieldException, IllegalAccessException {
        //获取Bar实例
        Bar bar = new Bar();
        //获取Bar的val字段
        Field field = bar.getClass().getDeclaredField("val");
        //获取val字段上的Foo注解实例
        Foo foo = field.getAnnotation(Foo.class);
        //获取 foo 这个代理实例所持有的 InvocationHandler
        InvocationHandler h = Proxy.getInvocationHandler(foo);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) hField.get(h);
        // 修改 value 属性值
        memberValues.put("value", "ddd");
        // 获取 foo 的 value 属性值
        String value = foo.value();
        // ddd
        System.out.println(value);
    }
}
