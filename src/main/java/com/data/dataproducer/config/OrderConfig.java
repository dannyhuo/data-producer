package com.data.dataproducer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class OrderConfig {

    @Value("${data.producer.order.24h.per-schedule.max}")
    private String hour24MaxStr;


    @Bean
    public Map<Integer, Integer> hour24Max () {
        Map<Integer, Integer> hour24Max = new HashMap<>(24);
        String[] arr = hour24MaxStr.split(",");
        for (String t : arr) {
            String[] arr2 = t.split(":");
            hour24Max.put(Integer.parseInt(arr2[0]), Integer.parseInt(arr2[1]));
        }
        return hour24Max;
    }
}

