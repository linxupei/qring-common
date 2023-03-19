package com.qring.common.test.common.config;

import com.qring.common.web.exception.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Qring
 * @Description 全局异常处理
 * @Date 2023/3/18 22:19
 * @Version 1.0
 */
@RestControllerAdvice
public class TestExceptionHandler extends GlobalExceptionHandler {
}
