package com.lifeflow.api.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.lifeflow.api.auth.dtos.RegisterUserRequest;
import com.lifeflow.api.user.dtos.CreateUserRequest;
import com.lifeflow.api.user.dtos.UpdateUserRequest;
import com.lifeflow.api.user.dtos.UserDetailsDto;
import com.lifeflow.api.user.entities.UserEntity;


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    
    UserEntity toEntity(CreateUserRequest request);

    UserEntity toRegEntity(RegisterUserRequest request);

    UserDetailsDto toDto(UserEntity user);

    void update(UpdateUserRequest request, @MappingTarget UserEntity user);
}

