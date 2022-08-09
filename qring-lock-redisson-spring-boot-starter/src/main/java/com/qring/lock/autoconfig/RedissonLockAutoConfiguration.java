package com.qring.lock.autoconfig;

import com.qring.lock.executor.RedissonLockExecutor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author Qring
 * @Description Redisson自动配置类
 * @Date 2022/8/9 15:25
 * @Version 1.0
 */
@Configuration
@ConditionalOnClass(Redisson.class)
public class RedissonLockAutoConfiguration {
    @Bean
    @Order(100)
    public RedissonLockExecutor redissonLockExecutor(RedissonClient redissonClient) {
        return new RedissonLockExecutor(redissonClient);
    }
}
