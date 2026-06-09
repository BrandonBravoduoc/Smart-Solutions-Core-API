package com.smarth.solutions.core.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarth.solutions.core.api.model.entity.UserSubscription;
import com.smarth.solutions.core.api.model.enums.SubscriptionStatus;


@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findByUserId(Long userId);

    List<UserSubscription> findByCurrentPeriodEndBeforeAndStatusIn(LocalDateTime now, List<SubscriptionStatus> of);

}
