package com.qring.common.base.annotation.qring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/8 17:01
 * @Version 1.0
 */
@Configuration
public class QringConfig {

    @Bean
    @ConditionalOnMissingBean
    public QringInterceptor qringInterceptor() {
        return new QringInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public QringAnnotationAdvisor qringAnnotationAdvisor(QringInterceptor qringInterceptor) {
        return new QringAnnotationAdvisor(qringInterceptor, Ordered.HIGHEST_PRECEDENCE);
    }
}
