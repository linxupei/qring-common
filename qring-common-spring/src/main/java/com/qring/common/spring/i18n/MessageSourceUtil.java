package com.qring.common.spring.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

/**
 * @Author Qring
 * @Description 语言资源工具
 * @Date 2023/3/15 22:25
 * @Version 1.0
 */
@Slf4j
@Component
public class MessageSourceUtil {

    @Resource
    private MessageSource messageSource;

    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public String getMessageByDefaultLocale(String key) {
        return this.messageSource.getMessage(key, null, null, getDefaultLocale());
    }

    public String getMessageByDefaultLocale(String key, String defaultMessage) {
        return this.messageSource.getMessage(key, null, defaultMessage, getDefaultLocale());
    }

    public String getMessageByDefaultLocale(String key, String... params) {
        return this.messageSource.getMessage(key, params, null, getDefaultLocale());
    }

    public String getMessageByDefaultLocale(String key, String defaultMessage, String... params) {
        return this.messageSource.getMessage(key, params, defaultMessage, getDefaultLocale());
    }

    public String getMessageByCurrentLocale(String key, Locale locale) {
        return this.messageSource.getMessage(key, null, null, locale);
    }

    public String getMessageByCurrentLocale(String key, String defaultMessage, Locale locale) {
        return this.messageSource.getMessage(key, null, defaultMessage, locale);
    }

    public String getMessageByCurrentLocale(String key, Locale locale, String... params) {
        return this.messageSource.getMessage(key, params, null, locale);
    }

    public String getMessageByCurrentLocale(String key, String defaultMessage, Locale locale, String... params) {
        return this.messageSource.getMessage(key, params, defaultMessage, locale);
    }

    private Locale getDefaultLocale() {
        HttpServletRequest request = getRequest();
        if (Objects.nonNull(request)) {
            return RequestContextUtils.getLocale(request);
        }
        return Locale.getDefault();
    }
}
