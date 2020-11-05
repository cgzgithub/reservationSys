package com.ust.shbay.manager.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * .
 */
@SpringBootApplication(scanBasePackages = "com.ust.shbay")
@EnableScheduling
@EnableAsync
@MapperScan(value ={"com.ust.shbay.manager.dao"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
