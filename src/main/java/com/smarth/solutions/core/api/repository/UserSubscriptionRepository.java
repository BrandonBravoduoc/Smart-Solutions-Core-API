package com.smarth.solutions.core.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarth.solutions.core.api.model.UserSubscription;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByUserId(String userId);
}
