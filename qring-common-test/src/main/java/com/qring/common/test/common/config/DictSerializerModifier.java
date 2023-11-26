package com.qring.common.test.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/7/10 19:50
 * @Version 1.0
 */
@Slf4j
public class DictSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter beanProperty : beanProperties) {
            Dict dict = beanProperty.getAnnotation(Dict.class);
            if (dict != null) {
                beanProperty.assignSerializer(new DictSerializer(dict));
            }
        }
        return beanProperties;
    }


    /**
     * 字典自定义序列化
     */
    static class DictSerializer extends JsonSerializer<Object> {
        /**
         * 生成序列化字段后缀
         */
        private static final String LABEL_SUFFIX = "Desc";
        /**
         * 字典配置信息
         */
        private final Dict dict;

        /**
         * 构造方法
         *
         * @param dict 字典描述
         */
        public DictSerializer(Dict dict) {
            this.dict = dict;
        }

        /**
         * Method that can be called to ask implementation to serialize
         * values of type this serializer handles.
         *
         * @param value       Value to serialize; can <b>not</b> be null.
         * @param gen         Generator used to output resulting Json content
         * @param serializers Provider that can be used to get serializers for
         *                    serializing Objects value contains, if any.
         */
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            serializers.defaultSerializeValue(value, gen);
            // 添加转换之后的字段：xxxDesc
            String fieldName = gen.getOutputContext().getCurrentName();
            gen.writeStringField(fieldName.concat(LABEL_SUFFIX), value != null ? this.getDesc(dict, value) : null);
        }


        /**
         * 获取字典信息
         *
         * @param dict  字典对象
         * @param value 字典值
         * @return ss
         */
        private String getDesc(Dict dict, Object value) {
            try {
                return dict.defaultValue();
            } catch (Exception e) {
                log.error("字典转换：获取字典描述异常,使用默认值：{}，key:{}, dict:{}, 异常：{}", dict.defaultValue(), value, dict.enumType(), e.getMessage(), e);
                return dict.defaultValue();
            }
        }
    }
}

