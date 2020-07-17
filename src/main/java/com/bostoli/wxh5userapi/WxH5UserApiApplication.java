package com.bostoli.wxh5userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableScheduling
public class WxH5UserApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxH5UserApiApplication.class, args);
    }
}
