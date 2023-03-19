package com.qring.common.web.i18n;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @Author Qring
 * @Description 国际化请求头
 * @Date 2023/3/15 22:43
 * @Version 1.0
 */
public class I18nDataAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    private static final String LANGUAGE = "Accept-Language";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String reqLanguage = request.getHeader(LANGUAGE);
        if (StrUtil.isBlank(reqLanguage)) {
            reqLanguage = request.getHeader(LANGUAGE.toLowerCase());
        }
        if (StrUtil.isNotBlank(reqLanguage)) {
            if (reqLanguage.contains(Locale.CHINESE.getLanguage())) {
                return Locale.SIMPLIFIED_CHINESE;
            }
            if (reqLanguage.contains(Locale.ENGLISH.getLanguage())) {
                return Locale.US;
            }
        }
        return Locale.SIMPLIFIED_CHINESE;
    }
}
