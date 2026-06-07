package com.smarth.solutions.core.api.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarth.solutions.core.api.model.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByIsActiveTrue();

    
    
}
