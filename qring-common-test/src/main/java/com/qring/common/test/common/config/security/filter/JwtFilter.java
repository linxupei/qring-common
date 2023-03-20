package com.qring.common.test.common.config.security.filter;

import cn.hutool.json.JSONUtil;
import com.qring.common.test.common.config.security.SecurityProperties;
import com.qring.common.test.common.util.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @Author Qring
 * @Description Jwt校验
 * @Date 2023/3/20 22:28
 * @Version 1.0
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkJwtToken(request)) {
            validateToken(request)
                    .filter(claims -> claims.get("authorities") != null)
                    .ifPresentOrElse(
                            this::setupSpringAuthentication,
                            SecurityContextHolder::clearContext
                    );
        }
        filterChain.doFilter(request, response);
    }

    private void setupSpringAuthentication(Claims claims) {
        val rawList = JSONUtil.toList((String) claims.get("authorities"), String.class);
        val authorities = rawList.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
        val authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<Claims> validateToken(HttpServletRequest req) {
        String jwtToken = req.getHeader(securityProperties.getJwt().getHeader()).replace(securityProperties.getJwt().getPrefix(), "");
        try {
            return Optional.of(Jwts.parserBuilder().setSigningKey(JwtUtil.KEY).build().parseClaimsJws(jwtToken).getBody());
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 检查 JWT Token 是否在 HTTP 报头中
     *
     * @param req HTTP 请求
     * @return 是否有 JWT Token
     */
    private boolean checkJwtToken(HttpServletRequest req) {
        String authenticationHeader = req.getHeader(securityProperties.getJwt().getHeader());
        return authenticationHeader != null && authenticationHeader.startsWith(securityProperties.getJwt().getPrefix());
    }
}
