package com.qring.common.test.statemachine;

import com.qring.common.test.statemachine.entity.Order;
import com.qring.common.test.statemachine.event.OrderEvent;
import com.qring.common.test.statemachine.service.OrderService;
import com.qring.common.test.statemachine.state.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;

@SpringBootTest
public class StateMachineTest {
    @Autowired
    private StateMachine<OrderStatus, OrderEvent> stateMachine;

    @Resource
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void test() {
        // 创建订单
        Order order = new Order();

        // 触发订单支付事件
        orderService.processPayment(order);

        // 触发订单发货事件
        orderService.processShipping(order);

        // 触发订单完成事件
        orderService.processCompletion(order);
    }
}
