package com.xl.soa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableEurekaClient
@MapperScan("com.xl.soa.mapper")
public class SoaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoaApplication.class, args);
    }

}
