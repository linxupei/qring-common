package com.qring.common.test.controller;

import com.qring.common.test.repository.mapper.TimeMapper;
import com.qring.common.test.repository.model.dto.DTO;
import com.qring.common.test.repository.model.vo.TimeVO;
import com.qring.common.test.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/31 10:18
 * @Version 1.0
 */
@RestController
public class TestController {
    @Resource
    private TimeMapper timeMapper;
    @Resource
    private TestService testService;

    @PostMapping("test")
    public void test(@RequestBody DTO dto) {
        System.out.println(dto);
        //return dto;
    }

    @GetMapping("test2")
    public void test2() {
        testService.test();
    }

    @GetMapping("test3")
    public Mono<TimeVO> test3() {
        return Mono.justOrEmpty(testService.test());
    }

    @GetMapping("test1.")
    public List<TimeVO> test1() {
        return timeMapper.list();
    }
}
