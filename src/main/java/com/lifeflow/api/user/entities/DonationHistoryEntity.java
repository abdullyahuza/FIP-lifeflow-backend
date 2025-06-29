package com.lifeflow.api.user.entities;

import java.time.Instant;
import java.time.LocalDate;

import com.lifeflow.api.donation.entities.DonationCenterEntity;

// import com.lifeflow.api.donation.entities.DonationCenterEntity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "donation_histories")
public class DonationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private DonationCenterEntity center;

    @Column(name = "donated_on")
    private LocalDate donatedOn;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
}

