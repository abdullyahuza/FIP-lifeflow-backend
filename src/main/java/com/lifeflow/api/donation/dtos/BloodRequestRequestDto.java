package com.lifeflow.api.donation.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BloodRequestRequestDto {
    private Long requestedById;
    private String bloodType;
    private Integer unitsNeeded;
    private String urgency;
    private String location;
    private LocalDateTime requestedAt;
    private boolean fulfilled;
    private Long centerId;
}
