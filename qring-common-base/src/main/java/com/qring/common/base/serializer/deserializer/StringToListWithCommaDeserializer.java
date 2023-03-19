package com.qring.common.base.serializer.deserializer;

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
 * @Description 将字符串按照逗号分割为List
 * @Date 2023/3/19 16:51
 * @Version 1.0
 */
public class StringToListWithCommaDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser p, DeserializationContext ctx) throws IOException {

        if (ObjectUtils.isEmpty(p)) {
            return Collections.emptyList();
        }

        return Arrays.stream(p.getText().split(",")).collect(Collectors.toList());
    }
}
