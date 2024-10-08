package com.qring.common.test.statemachine.service;

import com.qring.common.test.statemachine.entity.Order;
import com.qring.common.test.statemachine.event.OrderEvent;
import com.qring.common.test.statemachine.state.OrderStatus;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final StateMachine<OrderStatus, OrderEvent> stateMachine;

    public OrderService(StateMachine<OrderStatus, OrderEvent> stateMachine) {
        this.stateMachine = stateMachine;
    }

    // 处理订单支付事件
    public void processPayment(Order order) {
        // 发送 PAY 事件触发状态转换
        stateMachine.sendEvent(OrderEvent.PAY);
    }

    // 处理订单发货事件
    public void processShipping(Order order) {
        // 发送 SHIP 事件触发状态转换
        stateMachine.sendEvent(OrderEvent.SHIP);
    }

    // 处理订单完成事件
    public void processCompletion(Order order) {
        // 发送 COMPLETE 事件触发状态转换
        stateMachine.sendEvent(OrderEvent.COMPLETE);
    }
}

