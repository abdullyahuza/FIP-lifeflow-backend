package com.lifeflow.api.user.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
    private String phone;
}
