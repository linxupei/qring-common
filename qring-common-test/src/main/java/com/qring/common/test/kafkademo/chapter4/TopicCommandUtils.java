package com.qring.common.test.kafkademo.chapter4;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 代码清单4-1 & 4-2
 * Created by 朱小厮 on 2018/9/9.
 */
public class TopicCommandUtils {

    public static void main(String[] args) {
        //       createTopic();
//        describeTopic();
        listTopic();
//        List<BigDecimal> list = new ArrayList<>();
//        list.add(BigDecimal.ZERO);
//        list.add(BigDecimal.ONE);
//        list.add(new BigDecimal("3"));
//        list.add(null);
//        list.add(null);
//        list.add(null);
//        BigDecimal result = list.stream().reduce(BigDecimal.ZERO, NumberUtil::add);
//        System.out.println(result);
    }

    /**
     * 代码清单4-1
     */
    public static void createTopic() {
        String[] options = new String[]{
                "--zookeeper", "node1:2181/kafka",
                "--create",
                "--replication-factor", "1",
                "--partitions", "1",
                "--topic", "topic-create-test" + ThreadLocalRandom.current().nextInt()
        };
        kafka.admin.TopicCommand.main(options);
    }

    /**
     * 代码清单4-2
     */
    public static void describeTopic() {
        String[] options = new String[]{
                "--zookeeper", "node1:2181/kafka",
                "--describe",
                "--topic", "topic-create"
        };
        kafka.admin.TopicCommand.main(options);
    }

    public static void listTopic() {
        String[] options = new String[]{
                "--zookeeper", "node1:2181/kafka",
                "--list"
        };
        kafka.admin.TopicCommand.main(options);
    }

}
