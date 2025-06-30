package com.lifeflow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String home() {
        return "Welcome to LifeFlow API\nAPI is up & Running!";
    }
}
