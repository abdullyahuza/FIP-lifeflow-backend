package com.lifeflow.api.donation.entities;

import java.time.Instant;
import java.time.LocalDateTime;

import com.lifeflow.api.common.entities.Auditable;
import com.lifeflow.api.user.entities.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "blood_requests")
public class BloodRequestEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who made the request (can be hospital staff or patient)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requested_by_id", nullable = false)
    private UserEntity requestedBy;

    @Column(name = "blood_type", nullable = false, length = 5)
    private String bloodType;

    @Column(name = "units_needed", nullable = false)
    private Integer unitsNeeded;

    @Column(name = "urgency", nullable = false, length = 20)
    private String urgency; // e.g., low, medium, high

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "fulfilled", nullable = false)
    private boolean fulfilled;

    // Donation center where the blood is requested from
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "center_id", nullable = false)
    private DonationCenterEntity center;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
}
