package com.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AccountApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountApp.class, args);
        log.info("Account服务已经启动...");
    }
}
