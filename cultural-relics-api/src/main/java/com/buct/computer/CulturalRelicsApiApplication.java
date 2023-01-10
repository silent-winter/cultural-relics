package com.buct.computer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.buct.computer.mapper"})
public class CulturalRelicsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CulturalRelicsApiApplication.class, args);
    }

}
