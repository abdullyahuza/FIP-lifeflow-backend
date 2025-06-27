package com.lifeflowbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LifeflowApiApp {
    public static void main(String[] args) {
        SpringApplication.run(LifeflowApiApp.class, args);
        System.out.println("Hello and welcome");
    }
    
}