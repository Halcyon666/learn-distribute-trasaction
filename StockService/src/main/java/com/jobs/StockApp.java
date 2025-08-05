package com.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
        log.info("Stock服务已经启动...");
    }
}
