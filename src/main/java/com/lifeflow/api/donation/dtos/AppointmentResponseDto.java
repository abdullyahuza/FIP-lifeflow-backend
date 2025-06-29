package com.lifeflow.api.donation.dtos;

import com.lifeflow.api.donation.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDto {
    private Long id;
    private Long userId;
    private String userName;       // optional: full name of donor
    private Long centerId;
    private String centerName;     // optional: name of center
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
    private LocalDateTime cancelledAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
