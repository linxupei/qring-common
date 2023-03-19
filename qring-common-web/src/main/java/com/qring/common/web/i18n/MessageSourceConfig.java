package com.qring.common.web.i18n;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @Author Qring
 * @Description 语言国际化配置
 * @Date 2023/3/15 22:49
 * @Version 1.0
 */
@Slf4j
@Configuration
public class MessageSourceConfig {

    private static final String BASENAME_SPLIT = ";";
    private static final String DEFAULT_ENCODING = "UTF-8";
    @Value("${spring.messages.basename}")
    private String path;

    @Bean({"messageSource"})
    public MessageSource messageSource() {
        if (StrUtil.isBlank(this.path)) {
            log.error("{}", this.path);
            return null;
        }
        log.info("{}", this.path);
        CustomMessageSource messageSource = new CustomMessageSource();
        String[] paths = this.path.split(BASENAME_SPLIT);
        messageSource.setBasenames(paths);
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new I18nDataAcceptHeaderLocaleResolver();
    }
}
