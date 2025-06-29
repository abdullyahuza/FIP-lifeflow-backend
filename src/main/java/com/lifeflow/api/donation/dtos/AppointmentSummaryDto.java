package com.lifeflow.api.donation.dtos;

import com.lifeflow.api.donation.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentSummaryDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}
