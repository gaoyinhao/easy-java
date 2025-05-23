package com.easyjava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gao98
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.easyjava.mappers"})
public class RunDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunDemoApplication.class, args);
    }
}
