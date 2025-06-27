package com.lifeflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.lifeflow.api.common.config.SuperAdminProperties;

@SpringBootApplication
@ComponentScan(basePackages = "com.lifeflow.api")
@EnableConfigurationProperties(SuperAdminProperties.class)
public class LifeflowApiApp {
    public static void main(String[] args) {
        SpringApplication.run(LifeflowApiApp.class, args);
    }
    
}