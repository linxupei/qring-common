package com.qring.lock.autoconfig;

import com.qring.lock.DefaultLockFailureStrategy;
import com.qring.lock.LockFailureStrategy;
import com.qring.lock.LockTemplate;
import com.qring.lock.aop.AnnotationBeanPostProcessor;
import com.qring.lock.executor.LockExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Qring
 * @Description 锁自动配置
 * @Date 2022/8/9 10:25
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
@RequiredArgsConstructor
public class LockAutoConfig {

    private final LockProperties lockProperties;

    @SuppressWarnings("rawtypes")
    @Bean
    @ConditionalOnMissingBean
    public LockTemplate lockTemplate(List<LockExecutor> executors) {
        LockTemplate lockTemplate = new LockTemplate();
        lockTemplate.setProperties(lockProperties);
        lockTemplate.setExecutors(executors);
        return lockTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockFailureStrategy lockFailureStrategy() {
        return new DefaultLockFailureStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public AnnotationBeanPostProcessor annotationBeanPostProcessor(LockTemplate lockTemplate, LockFailureStrategy lockFailureStrategy) {
        return new AnnotationBeanPostProcessor(lockTemplate, lockFailureStrategy);
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public LockAnnotationAdvisor lockAnnotationAdvisor(LockInterceptor lockInterceptor) {
//        return new LockAnnotationAdvisor(lockInterceptor, Ordered.HIGHEST_PRECEDENCE);
//    }

//    @Bean
//    @ConditionalOnMissingBean
//    public AnnotationBeanPostProcessor annotationBeanPostProcessor(LockAnnotationAdvisor lockAnnotationAdvisor) {
//        return new AnnotationBeanPostProcessor(lockAnnotationAdvisor);
//    }
}
