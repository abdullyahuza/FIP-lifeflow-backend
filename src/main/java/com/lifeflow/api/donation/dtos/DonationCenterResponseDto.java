package com.lifeflow.api.donation.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationCenterResponseDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String contactPhone;
    private String hoursOpen;
    private Instant createdAt;
    private Instant updatedAt;
}
