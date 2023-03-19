package com.qring.common.test.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qring.common.test.common.config.security.filter.RestAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Qring
 * @Description 安全配置
 * @Date 2023/3/19 17:07
 * @Version 1.0
 */
@Slf4j
@EnableWebSecurity
@Order(99)
public class WebSecurityConfig {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private UserDetailsPasswordService userDetailsPasswordService;

    public static void main(String[] args) {
        PasswordEncoder encoder = new WebSecurityConfig().passwordEncoder();
        System.out.println(encoder.encode("123456"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 请求匹配, 配置的是哪些url进行安全控制
                .requestMatchers(req -> req.mvcMatchers("/api/**", "admin/**", "/authorize/**", "multiple/thread/**"))
                // 授权请求, 配置的是如何进行控制
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("admin/**").hasRole("ADMIN")
                        .antMatchers("api/**").hasRole("USER")
                        .anyRequest().authenticated())
                // session
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 异常处理
//                .exceptionHandling(handing ->
//                        handing.authenticationEntryPoint((request, response, exception) -> handler.resolveException(request, response, null, exception))
//                                .accessDeniedHandler((request, response, exception) -> handler.resolveException(request, response, null, exception)))
                // 跨域
                .csrf(AbstractHttpConfigurer::disable)
                // 表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 启用认证头, 可以通过请求头将用户密码发过来
                .httpBasic(Customizer.withDefaults())
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(
                "/authorize/**",
                "/error/**",
                "/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 默认编码算法的 Id
        val idForEncode = "bcrypt";
        // 要支持的多种编码器
        val encoders = Map.of(
                idForEncode, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1")
        );
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter(objectMapper);
        filter.setAuthenticationSuccessHandler(jsonLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(jsonLoginFailureHandler());
        filter.setFilterProcessesUrl("/authorize/login");
        return filter;
    }

    private LogoutSuccessHandler jsonLogoutSuccessHandler() {
        return (req, res, auth) -> {
            if (auth != null && auth.getDetails() != null) {
                req.getSession().invalidate();
            }
            res.setStatus(HttpStatus.OK.value());
            res.getWriter().println();
            log.debug("成功退出登录");
        };
    }

    private AuthenticationSuccessHandler jsonLoginSuccessHandler() {
        return (req, res, auth) -> {
            res.setStatus(HttpStatus.OK.value());
            res.getWriter().println();
            log.debug("认证成功");
        };
    }

    private AuthenticationFailureHandler jsonLoginFailureHandler() {
        return (req, res, exp) -> {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            val errData = Map.of(
                    "title", "认证失败",
                    "details", exp.getMessage()
            );
            res.getWriter().println(objectMapper.writeValueAsString(errData));
        };
    }
}
