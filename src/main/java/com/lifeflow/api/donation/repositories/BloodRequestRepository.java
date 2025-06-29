package com.lifeflow.api.donation.repositories;

import com.lifeflow.api.donation.entities.BloodRequestEntity;
import com.lifeflow.api.user.entities.UserEntity;
import com.lifeflow.api.donation.entities.DonationCenterEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequestEntity, Long> {

    List<BloodRequestEntity> findByFulfilled(boolean fulfilled);

    List<BloodRequestEntity> findByRequestedBy(UserEntity user);

    List<BloodRequestEntity> findByCenter(DonationCenterEntity center);

    List<BloodRequestEntity> findByBloodTypeIgnoreCase(String bloodType);

    List<BloodRequestEntity> findByUrgencyIgnoreCase(String urgency);

    List<BloodRequestEntity> findByLocationContainingIgnoreCase(String location);

    List<BloodRequestEntity> findByUrgencyIgnoreCaseAndFulfilled(String urgency, boolean fulfilled);

}
