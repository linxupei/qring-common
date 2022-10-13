package com.qring.common.test.enums;

import cn.hutool.core.util.EnumUtil;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/12 9:38
 * @Version 1.0
 */
public enum ETestType {
    /**
     *
     */
    ONE(1),
    TWO(2),
    THREE(3)
    ;
    private final int value;

    ETestType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args) {
        System.out.println(EnumUtil.fromString(ETestType.class, "1"));
    }
}
