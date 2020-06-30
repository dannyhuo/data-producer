package com.data.dataproducer.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author danny
 * @date 2020/6/30 9:51 PM
 */
@Slf4j
@Data
@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${kafka.producer.user-topic}")
    private String userTopic;
    @Value("${kafka.producer.order-topic}")
    private String orderTopic;
    @Value("${kafka.producer.coupon-topic}")
    private String couponTopic;

    @Value("${kafka.message.advice.enable}")
    private boolean kafkaEnable = false;

//    @Value("${kafka.producer.servers}")
//    private String servers;
//
//    @Value("${kafka.producer.retries}")
//    private int retries;
//
//    @Value("${kafka.producer.batch.size}")
//    private int batchSize;
//
//    @Value("${kafka.producer.linger}")
//    private int linger;
//
//    @Value("${kafka.producer.buffer.memory}")
//    private int bufferMemory;
//
//
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>(9);
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        props.put(ProducerConfig.RETRIES_CONFIG, retries);
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
//        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }


}
