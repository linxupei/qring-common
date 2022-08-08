package com.qring.common.base.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @Author Qring
 * @Description LocalDate序列化成时间戳
 * @Date 2022/7/4 15:36
 * @Version 1.0
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}