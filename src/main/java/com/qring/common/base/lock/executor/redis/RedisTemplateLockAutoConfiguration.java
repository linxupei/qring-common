package com.qring.common.base.lock.executor.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author Qring
 * @Description Redis自动配置
 * @Date 2022/8/9 14:07
 * @Version 1.0
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisTemplateLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate();
    }

    @Bean
    @Order(200)
    public RedisTemplateLockExecutor redisTemplateLockExecutor(StringRedisTemplate stringRedisTemplate) {
        return new RedisTemplateLockExecutor(stringRedisTemplate);
    }
}
