package com.smarth.solutions.core.api.dto;

import com.smarth.solutions.core.api.model.entity.SubscriptionHistory;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SubscriptionHistoryDTO {

    public record Response(
            Long id,
            Long subscriptionId,
            String subscriptionName,
            BigDecimal subscriptionPrice,
            LocalDateTime periodStart,
            LocalDateTime periodEnd,
            String action,
            LocalDateTime changedAt
    ) {
        public static Response fromEntity(SubscriptionHistory h) {
            return new Response(
                    h.getId(),
                    h.getSubscriptionId(),
                    h.getSubscriptionName(),
                    h.getSubscriptionPrice(),
                    h.getPeriodStart(),
                    h.getPeriodEnd(),
                    h.getAction().name(),
                    h.getChangedAt()
            );
        }
    }
}