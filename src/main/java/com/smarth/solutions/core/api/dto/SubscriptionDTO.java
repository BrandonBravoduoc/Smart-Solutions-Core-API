package com.smarth.solutions.core.api.dto;

import java.math.BigDecimal;

import com.smarth.solutions.core.api.model.entity.Subscription;

public class SubscriptionDTO {
    
    public record Request(
            String name,
            String details,
            BigDecimal price,
            Integer durationMonths,
            boolean isActive
    ) {}

    public record Response(
            Long id,
            String name,
            String details,
            BigDecimal price,
            Integer durationMonths,
            boolean isActive
    ) {
        public static Response fromEntity(Subscription plan) {
            return new Response(
                    plan.getId(),
                    plan.getName(),
                    plan.getDetails(),
                    plan.getPrice(),
                    plan.getDurationMonths(),
                    plan.isActive()
            );
        }
    }
}
