package com.qring.common.test;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Qring
 * @Description 启动类
 * @Date 2022/8/18 14:18
 * @Version 1.0
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = {"com.qring.common.test.repository"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
