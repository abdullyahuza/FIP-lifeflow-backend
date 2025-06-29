package com.lifeflow.api.donation.repositories;

import com.lifeflow.api.donation.entities.AppointmentEntity;
import com.lifeflow.api.donation.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    // Find all appointments for a specific user
    List<AppointmentEntity> findByUserId(Long userId);

    // Find all appointments for a specific donation center
    List<AppointmentEntity> findByCenterId(Long centerId);

    // Find appointments by status
    List<AppointmentEntity> findByStatus(AppointmentStatus status);

    // Optional: Find upcoming appointments
    List<AppointmentEntity> findByAppointmentTimeAfter(LocalDateTime time);

    // Optional: Find appointments by user and date range
    List<AppointmentEntity> findByUserIdAndAppointmentTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
