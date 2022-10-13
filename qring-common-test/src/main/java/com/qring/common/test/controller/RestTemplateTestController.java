package com.qring.common.test.controller;

import cn.hutool.json.JSONUtil;
import com.qring.common.test.repository.mapper.TimeMapper;
import com.qring.common.test.repository.model.dto.DTO;
import com.qring.common.test.repository.model.vo.TimeVO;
import com.qring.common.test.service.TestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/31 10:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/rest/template")
@Slf4j
public class RestTemplateTestController {
    @Resource
    private TimeMapper timeMapper;
    @Resource
    private TestService testService;

    @PostMapping("test")
    public void test(@RequestBody DTO dto) {
        System.out.println(dto);
        //return dto;
    }

    @PostMapping("test20")
    public DTO test20(@RequestParam("file") MultipartFile file,
                               @RequestParam("filename") String filename) {
        File folder = new File("C:\\Users\\DELL\\Desktop\\test");
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        try {
            // 文件保存
            file.transferTo(new File(folder, filename + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DTO dto = new DTO();
        dto.setStr("123");
        dto.setList(Arrays.asList("1", "2"));
        return dto;
    }

    @GetMapping(value = "test21")
    public void test21(@RequestParam(value = "filename") String filename,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        File file = new File("E:\\download" + File.separator + filename);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            String extendFileName = filename.substring(filename.lastIndexOf('.'));
            // 动态设置响应类型，根据前台传递文件类型设置响应类型
            response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
            // 设置响应头,attachment表示以附件的形式下载，inline表示在线打开
            response.setHeader("content-disposition","attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
            // 获取输出流对象（用于写文件）
            OutputStream os = response.getOutputStream();
            // 下载文件,使用spring框架中的FileCopyUtils工具
            FileCopyUtils.copy(fis,os);
        }
    }

    @PostMapping("test10")
    public DTO test10(@RequestParam(name = "name", required = false) String name,
                       @RequestParam(name = "age", required = false) Integer age) {
        System.out.println(name + ", " + age);
        DTO dto = new DTO();
        dto.setStr("123");
        dto.setList(Arrays.asList("1", "2"));
        return dto;
    }

    @Data
    static class Person {
        String name;
        Integer age;
    }

    @PostMapping("test11")
    public DTO test11(@RequestBody Person person) {
        System.out.println(person.getName() + ", " + person.getAge());
        DTO dto = new DTO();
        dto.setStr("123");
        dto.setList(Arrays.asList("1", "2"));
        return dto;
    }

    @GetMapping("test00")
    public DTO test00(HttpServletRequest request) {
        log.info("{}", JSONUtil.parse(request));
        DTO dto = new DTO();
        dto.setStr("123");
        dto.setList(Arrays.asList("1", "2"));
        return dto;
    }

    @GetMapping("test01")
    public DTO test01(@RequestParam(name = "name", required = false) String name,
                      @RequestParam(name = "age", required = false) Integer age) {
        System.out.println(name + ", " + age);
        DTO dto = new DTO();
        dto.setStr("123");
        dto.setList(Arrays.asList("1", "2"));
        return dto;
    }

    @GetMapping("test02/{name}/{age}")
    public DTO test02(@PathVariable(name = "name", required = false) String name,
                      @PathVariable(name = "age", required = false) Integer age) {
        System.out.println(name + ", " + age);
        DTO dto = new DTO();
        dto.setStr("123");
        dto.setList(Arrays.asList("1", "2"));
        return dto;
    }

    @GetMapping("test3")
    public void test3() {
    }

    @GetMapping("test2")
    public void test2() {
        testService.test();
    }

    @GetMapping("test1")
    public List<TimeVO> test1() {
        return timeMapper.list();
    }
}
