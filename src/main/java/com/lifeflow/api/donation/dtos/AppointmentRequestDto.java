package com.lifeflow.api.donation.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDto {
    private Long userId;
    private Long centerId;
    private LocalDateTime appointmentTime;
}
