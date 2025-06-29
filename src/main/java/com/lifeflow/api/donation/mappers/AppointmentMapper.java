package com.lifeflow.api.donation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lifeflow.api.donation.dtos.AppointmentRequestDto;
import com.lifeflow.api.donation.dtos.AppointmentResponseDto;
import com.lifeflow.api.donation.dtos.AppointmentSummaryDto;
import com.lifeflow.api.donation.entities.AppointmentEntity;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AppointmentMapper {

    AppointmentSummaryDto toSummaryDto(AppointmentEntity entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "center.id", target = "centerId")
    @Mapping(source = "center.name", target = "centerName")
    AppointmentResponseDto toResponseDto(AppointmentEntity entity);

    @Mapping(target = "user", ignore = true)   
    @Mapping(target = "center", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    AppointmentEntity toEntity(AppointmentRequestDto dto);
}
