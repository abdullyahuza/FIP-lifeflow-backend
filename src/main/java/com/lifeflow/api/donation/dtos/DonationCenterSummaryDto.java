package com.lifeflow.api.donation.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationCenterSummaryDto {
    private Long id;
    private String name;
    private String city;
    private String state;
}
