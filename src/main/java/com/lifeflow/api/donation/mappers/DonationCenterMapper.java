package com.lifeflow.api.donation.mappers;

import com.lifeflow.api.donation.dtos.*;
import com.lifeflow.api.donation.entities.DonationCenterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DonationCenterMapper {

    DonationCenterMapper INSTANCE = Mappers.getMapper(DonationCenterMapper.class);

    DonationCenterEntity toEntity(DonationCenterRequestDto dto);

    DonationCenterResponseDto toResponseDto(DonationCenterEntity entity);

    DonationCenterSummaryDto toSummaryDto(DonationCenterEntity entity);
}
