package com.zxh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zxh.mapper")
public class SnackmallApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnackmallApplication.class, args);
    }
}
