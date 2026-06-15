package com.smarth.solutions.core.api.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smarth.solutions.core.api.model.enums.HistoryAction;

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
@Table(name = "subscription_history")
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionHistory {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "subscription_id", nullable = false)
    private Long subscriptionId;

    @Column(name = "subscription_name", nullable = false)
    private String subscriptionName;

    @Column(name = "subscription_price", nullable = false)
    private BigDecimal subscriptionPrice;

    @Column(name = "period_start", nullable = false)
    private LocalDateTime periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDateTime periodEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HistoryAction action;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;




}
