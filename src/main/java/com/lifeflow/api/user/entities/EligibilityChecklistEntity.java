package com.lifeflow.api.user.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "eligibility_checklists")
public class EligibilityChecklistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "responses", columnDefinition = "TEXT")
    private String responses; // or use @Lob or JSON mapping

    @Column(name = "is_eligible")
    private Boolean isEligible;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Column(name = "created_at")
    private LocalDate createdAt;
}

