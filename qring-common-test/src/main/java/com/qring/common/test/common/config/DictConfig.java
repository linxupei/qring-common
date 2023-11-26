package com.qring.common.test.common.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/7/10 19:49
 * @Version 1.0
 */
@Configuration
public class DictConfig {
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        SimpleModule simpleModule = new SimpleModule().setSerializerModifier(new DictSerializerModifier());
        builder.modules(simpleModule);
        return builder;
    }
}
