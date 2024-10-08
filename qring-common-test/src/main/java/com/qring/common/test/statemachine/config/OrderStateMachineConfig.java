package com.qring.common.test.statemachine.config;

import com.qring.common.test.statemachine.event.OrderEvent;
import com.qring.common.test.statemachine.state.OrderStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
// 启用状态机
@EnableStateMachine
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderEvent> {

    // 配置状态机的初始状态和所有可能的状态
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderStatus.CREATED) // 设置初始状态为 CREATED
                .states(EnumSet.allOf(OrderStatus.class)); // 添加所有可能的状态
    }

    // 配置状态机的状态转换规则
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal() // 定义外部状态转换
                .source(OrderStatus.CREATED).target(OrderStatus.PAID).event(OrderEvent.PAY) // 从 CREATED 状态转换到 PAID 状态，当触发 PAY 事件时
                .and() // 连接下一个状态转换
                .withExternal() // 定义外部状态转换
                .source(OrderStatus.PAID).target(OrderStatus.SHIPPED).event(OrderEvent.SHIP) // 从 PAID 状态转换到 SHIPPED 状态，当触发 SHIP 事件时
                .and() // 连接下一个状态转换
                .withExternal() // 定义外部状态转换
                .source(OrderStatus.SHIPPED).target(OrderStatus.COMPLETED).event(OrderEvent.COMPLETE); // 从 SHIPPED 状态转换到 COMPLETED 状态，当触发 COMPLETE 事件时
    }
}
