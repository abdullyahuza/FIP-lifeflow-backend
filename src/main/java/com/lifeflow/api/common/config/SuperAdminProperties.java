package com.lifeflow.api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "lifeflow.superadmin")
@Data
public class SuperAdminProperties {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
