package com.junlin.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Kafka 消息生产者
 */
public class ProducerFastStart {
    // Kafka集群地址
    private static final String brokerList = "192.168.175.129:9092";
    //    private static final String brokerList = "centos7:9092";
    // 主题名称-之前已经创建
    private static final String topic = "cavalier";

    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置key序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //另外一种写法
        //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 设置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);
        // 设置值序列化器
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 设置集群地址
        properties.put("bootstrap.servers", brokerList);
        // KafkaProducer 线程安全
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "Kafka-demo-001", "hello, cavalier2!");
        try {
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // 异常兜底
                }
            });
            //RecordMetadata recordMetadata = producer.send(record).get();
            //System.out.println("part:" + recordMetadata.partition() + ";topic:" + recordMetadata.topic());
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }
}
