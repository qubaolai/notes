package com.almond.aopstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AopStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopStudyApplication.class, args);
    }

}
