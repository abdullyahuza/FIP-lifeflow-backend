package com.lifeflow.api.donation.repositories;

import com.lifeflow.api.donation.entities.DonationCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationCenterRepository extends JpaRepository<DonationCenterEntity, Long> {

    // Find donation centers by city (case-insensitive)
    List<DonationCenterEntity> findByCityIgnoreCase(String city);

    // Find donation centers by state (case-insensitive)
    List<DonationCenterEntity> findByStateIgnoreCase(String state);

    // Find centers by city and state
    List<DonationCenterEntity> findByCityIgnoreCaseAndStateIgnoreCase(String city, String state);

    // Optional: Search by name containing keyword (case-insensitive)
    List<DonationCenterEntity> findByNameContainingIgnoreCase(String keyword);

    // Optional: Get all centers ordered by name
    List<DonationCenterEntity> findAllByOrderByNameAsc();

    boolean existsByNameIgnoreCase(String name);

    Optional<DonationCenterEntity> findByName(String name);


}
