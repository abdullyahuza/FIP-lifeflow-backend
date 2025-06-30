package com.lifeflow.api.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDetailsDto {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String role;
    private String createdAt;
    private String updatedAt;
}

