package com.data.dataproducer.proxy.jms;

import com.data.dataproducer.config.KafkaConfig;
import com.data.dataproducer.entity.AUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 用户注册通知
 *
 * @author danny
 *
 * @date 2019/6/28 4:48 PM
 *
 */
@Slf4j
@Component
public class UserRegisterMessageProxy {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 用户注册消息通知
     *
     * @param user
     */
    public void registerAdvice (AUser user) {
        if (kafkaConfig.isKafkaEnable()) {
            try {
                kafkaTemplate.send(kafkaConfig.getUserTopic(), user.getUserName(),
                        objectMapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                log.error("Write Auser to json error => " + e.getMessage(), e);
            }
        }
    }

}
