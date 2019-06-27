package com.data.dataproducer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.data.dataproducer.mapper")
@SpringBootApplication(scanBasePackages = {"com.data.dataproducer"})
public class DataProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataProducerApplication.class, args);
	}

}
