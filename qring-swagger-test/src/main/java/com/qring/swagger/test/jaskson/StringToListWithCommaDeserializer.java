package com.qring.swagger.test.jaskson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Qring
 * @Description 将字符串安装逗号分割为List
 * @Date 2022/8/30 19:18
 * @Version 1.0
 */
public class StringToListWithCommaDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser p, DeserializationContext ctx) throws IOException {

        if (ObjectUtils.isEmpty(p)) {
            return Collections.emptyList();
        }
        System.out.println("text: " + p.getText());
        return Arrays.stream(p.getText().split(",")).collect(Collectors.toList());
    }
}
