package com.lifeflow.api.user.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "donor_profiles")
public class DonorProfileEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "blood_type", length = 3, nullable = false)
    private String bloodType; // e.g., A+, O-

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "is_eligible")
    private Boolean isEligible; // based on latest eligibility check

    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;

    @Column(name = "total_donations")
    private Integer totalDonations;

    // Optional: Track verification status
    @Column(name = "is_verified")
    private Boolean isVerified;
}
