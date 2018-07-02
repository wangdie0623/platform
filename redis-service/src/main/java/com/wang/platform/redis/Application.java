package com.wang.platform.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class Application implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
       log.info("redis服务启动");
    }
}
