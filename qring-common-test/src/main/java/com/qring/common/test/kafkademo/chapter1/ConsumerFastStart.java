package com.qring.common.test.kafkademo.chapter1;

import cn.hutool.json.JSONUtil;
import com.qring.common.test.kafkademo.chapter2.Company;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 代码清单1-2
 * Created by 朱小厮 on 2018/7/21.
 */
public class ConsumerFastStart {
    public static final String brokerList = "192.168.219.131:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));
        //System.out.println(consumer.partitionsFor("topic-demo"));
        while (true) {
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                if (!records.isEmpty()) {
                    System.out.println(record.value());
                    System.out.println(JSONUtil.parse(record.value()).toBean(Company.class));
                }
            }
        }
    }
}
