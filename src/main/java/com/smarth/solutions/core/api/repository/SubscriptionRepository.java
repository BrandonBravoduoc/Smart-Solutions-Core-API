package com.smarth.solutions.core.api.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.enums.ApprovalStatus;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByIsActiveTrue();

    List<Subscription> findByIsActiveTrueAndApprovalStatus(ApprovalStatus approvalStatus);

    List<Subscription> findByApprovalStatus(ApprovalStatus approvalStatus);

    List<Subscription> findByProposedByUserId(Long proposedByUserId);

}
