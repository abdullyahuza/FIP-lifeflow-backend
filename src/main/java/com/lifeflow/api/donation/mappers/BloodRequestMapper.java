package com.lifeflow.api.donation.mappers;

import com.lifeflow.api.donation.dtos.BloodRequestRequestDto;
import com.lifeflow.api.donation.dtos.BloodRequestResponseDto;
import com.lifeflow.api.donation.entities.BloodRequestEntity;
import com.lifeflow.api.donation.entities.DonationCenterEntity;
import com.lifeflow.api.user.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class BloodRequestMapper {

    public BloodRequestEntity toEntity(BloodRequestRequestDto dto, UserEntity user, DonationCenterEntity center) {
        return BloodRequestEntity.builder()
                .requestedBy(user)
                .bloodType(dto.getBloodType())
                .unitsNeeded(dto.getUnitsNeeded())
                .urgency(dto.getUrgency())
                .location(dto.getLocation())
                .requestedAt(dto.getRequestedAt())
                .fulfilled(dto.isFulfilled())
                .center(center)
                .build();
    }

    public BloodRequestResponseDto toDto(BloodRequestEntity entity) {
        BloodRequestResponseDto dto = new BloodRequestResponseDto();
        dto.setId(entity.getId());
        dto.setRequestedById(entity.getRequestedBy() != null ? entity.getRequestedBy().getId() : null);
        dto.setRequestedByName(entity.getRequestedBy() != null ? entity.getRequestedBy().getFullName() : null); // assuming getFullName()
        dto.setBloodType(entity.getBloodType());
        dto.setUnitsNeeded(entity.getUnitsNeeded());
        dto.setUrgency(entity.getUrgency());
        dto.setLocation(entity.getLocation());
        dto.setRequestedAt(entity.getRequestedAt());
        dto.setFulfilled(entity.isFulfilled());
        dto.setCenterId(entity.getCenter() != null ? entity.getCenter().getId() : null);
        dto.setCenterName(entity.getCenter() != null ? entity.getCenter().getName() : null);
        return dto;
    }
}
