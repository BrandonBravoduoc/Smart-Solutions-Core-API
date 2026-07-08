package com.smarth.solutions.core.api.model.entity;

import java.math.BigDecimal;

import com.smarth.solutions.core.api.model.enums.ApprovalStatus;
import com.smarth.solutions.core.api.model.enums.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "subscription")
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

     @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer durationMonths;

    @Column(name = "is_active", nullable = false)
    private boolean  isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType = ServiceType.PRESENCIAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.APPROVED;

    @Column(name = "proposed_by_user_id")
    private Long proposedByUserId;

    @Column(name = "address_id")
    private Long addressId;

}
