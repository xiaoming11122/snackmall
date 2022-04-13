package com.zxh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.zxh.mapper")
@EnableAsync
public class SnackmallApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnackmallApplication.class, args);
    }
}
