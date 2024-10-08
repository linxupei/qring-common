package com.qring.common.test.statemachine.state;

public enum OrderStatus {
    CREATED, // 订单创建
    PAID,    // 订单支付
    SHIPPED, // 订单发货
    COMPLETED // 订单完成
}
