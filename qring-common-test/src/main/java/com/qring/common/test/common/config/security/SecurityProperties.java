package com.qring.common.test.common.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Qring
 * @Description 安全配置
 * @Date 2023/3/19 17:07
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "qring.security")
public class SecurityProperties {
    @Getter
    @Setter
    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt {

        private String header = "Authorization"; // HTTP 报头的认证字段的 key

        private String prefix = "Bearer "; // HTTP 报头的认证字段的值的前缀

        private long accessTokenExpireTime = 60 * 1000L; // Access Token 过期时间

        private long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L; // Refresh Token 过期时间
    }
}
