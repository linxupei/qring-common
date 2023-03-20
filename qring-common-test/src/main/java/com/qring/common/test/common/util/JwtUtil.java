package com.qring.common.test.common.util;

import cn.hutool.json.JSONUtil;
import com.qring.common.test.common.config.security.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Qring
 * @Description jwt工具类
 * @Date 2023/3/20 22:34
 * @Version 1.0
 */
@Component
public class JwtUtil {

    /**
     * 用于签名 Access Token
     */
    public static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    /**
     * 用于签名 Refresh Token
     */
    public static final Key REFRESH_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Resource
    private SecurityProperties securityProperties;

    public String createJwtToken(UserDetails userDetails, long timeToExpire) {
        return createJwtToken(userDetails, timeToExpire, KEY);
    }

    /**
     * 根据用户信息生成一个 JWT
     *
     * @param userDetails  用户信息
     * @param timeToExpire 毫秒单位的失效时间
     * @param signKey      签名使用的 key
     * @return JWT
     */
    public String createJwtToken(UserDetails userDetails, long timeToExpire, Key signKey) {
        return Jwts
                .builder()
                .setId("QR")
                .setSubject(userDetails.getUsername())
                .claim("authorities",
                        JSONUtil.toJsonStr(userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeToExpire))
                .signWith(signKey, SignatureAlgorithm.HS512).compact();
    }

    public String createAccessToken(UserDetails userDetails) {
        return createJwtToken(userDetails, securityProperties.getJwt().getAccessTokenExpireTime());
    }

    public String createRefreshToken(UserDetails userDetails) {
        return createJwtToken(userDetails, securityProperties.getJwt().getRefreshTokenExpireTime(), REFRESH_KEY);
    }

    public boolean validateAccessToken(String jwtToken) {
        return validateToken(jwtToken, KEY);
    }

    public boolean validateRefreshToken(String jwtToken) {
        return validateToken(jwtToken, REFRESH_KEY);
    }

    public boolean validateToken(String jwtToken, Key signKey) {
        try {
            Jwts.parserBuilder().setSigningKey(JwtUtil.KEY).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    public String buildAccessTokenWithRefreshToken(String jwtToken) {
        return parseClaims(jwtToken, REFRESH_KEY)
                .map(claims -> Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + securityProperties.getJwt().getAccessTokenExpireTime()))
                        .signWith(KEY, SignatureAlgorithm.HS512).compact())
                .orElseThrow();
    }

    public Optional<Claims> parseClaims(String jwtToken, Key signKey) {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(jwtToken).getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateWithoutExpiration(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(JwtUtil.KEY).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {
                return true;
            }
        }
        return false;
    }
}
