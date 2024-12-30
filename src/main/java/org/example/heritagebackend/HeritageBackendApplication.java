package org.example.heritagebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.heritagebackend.mapper")
public class HeritageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeritageBackendApplication.class, args);
    }

}
