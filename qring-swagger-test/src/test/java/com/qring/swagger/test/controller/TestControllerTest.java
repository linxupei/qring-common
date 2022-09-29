package com.qring.swagger.test.controller;


import com.qring.swagger.test.repository.model.dto.ResponseDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/22 10:59
 * @Version 1.0
 */
class TestControllerTest {

    @Test
    public void test20(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", new FileSystemResource(new File("C:\\Users\\DELL\\Desktop\\image\\image-20220808224258982.png")));
        param.add("filename", "test");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(param, headers);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.postForEntity("http://localhost:8080/test20", request, ResponseDTO.class);
        System.out.println(responseEntity.getBody());
    }

    /**
     * 将文件一次性读取到内存后, 再进行写盘, 遇到大文件时可能会 OOM
     * @throws IOException
     */
    @Test
    public void test21() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String filename = "test.zip";

        //发起请求,直接返回对象（restful风格）
        ResponseEntity<byte[]> rsp = restTemplate.getForEntity("http://localhost:8080/test21?filename={filename}", byte[].class, filename);
        System.out.println("文件下载请求结果状态码：" + rsp.getStatusCode());

        // 将下载下来的文件内容保存到本地
        String targetPath = "C:\\Users\\DELL\\Desktop\\test" + File.separator + filename;
        Files.write(Paths.get(targetPath), Objects.requireNonNull(rsp.getBody(), "未获取到下载文件"));
    }

    /**
     * 使用流式处理, 有多少写多少, 避免一次性读取到内存后造成 OOM
     */
    @Test
    public void test22() {
        RestTemplate restTemplate = new RestTemplate();
        String filename = "test.zip";

        // APPLICATION_OCTET_STREAM，表示以流的形式进行数据加载
        RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));

        String targetPath = "C:\\Users\\DELL\\Desktop\\test" + File.separator + filename;
        // 流式处理, 拿到一部分数据就写一部分数据, 而不是将所有数据拿到内存后再进行写盘
        restTemplate.execute("http://localhost:8080/test21?filename={filename}", HttpMethod.GET, requestCallback, clientHttpResponse -> {
            Files.copy(clientHttpResponse.getBody(), Paths.get(targetPath));
            return null;
        }, filename);
    }

    @Test
    public void test10() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        // 表单常见有两种类型
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        //接口参数
        map.add("name","xiaoming");
        map.add("age",9);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.postForEntity("http://localhost:8080/test10", entity, ResponseDTO.class);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void test11() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 如果有相应对象, 创建对象也可
        JSONObject param = new JSONObject();
        param.put("name", "xiaoming");
        param.put("age", 9);
        HttpEntity<String> entity = new HttpEntity<>(param.toString(), headers);
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.postForEntity("http://localhost:8080/test11", entity, ResponseDTO.class);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void test00() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "Bearer 10001");
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("http://localhost:8080/test00", HttpMethod.GET, entity, ResponseDTO.class);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void test01() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = new HashMap<>();
        //接口参数
        map.put("name","xiaoming01");
        map.put("age",9);
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/test01?name={name}&age={age}", ResponseDTO.class, map);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void test02() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = new HashMap<>();
        //接口参数
        map.put("name","xiaoming02");
        map.put("age",9);
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/test02/{name}/{age}", ResponseDTO.class, map);
        System.out.println(responseEntity.getBody());
    }
}