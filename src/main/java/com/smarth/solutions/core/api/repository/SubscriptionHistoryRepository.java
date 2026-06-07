package com.smarth.solutions.core.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarth.solutions.core.api.model.entity.SubscriptionHistory;

public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, Long> {

    List<SubscriptionHistory> findByUserIdOrderByChangedAtDesc(Long userId);
    
}
