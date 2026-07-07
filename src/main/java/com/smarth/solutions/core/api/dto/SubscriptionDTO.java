package com.smarth.solutions.core.api.dto;

import java.math.BigDecimal;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.enums.ApprovalStatus;
import com.smarth.solutions.core.api.model.enums.ServiceType;

public class SubscriptionDTO {

    public record Request(
            String name,
            String details,
            BigDecimal price,
            Integer durationMonths,
            boolean isActive,
            ServiceType serviceType,
            Long addressId
    ) {}

    public record Response(
            Long id,
            String name,
            String details,
            BigDecimal price,
            Integer durationMonths,
            boolean isActive,
            ServiceType serviceType,
            ApprovalStatus approvalStatus,
            Long proposedByUserId,
            Long addressId
    ) {
        public static Response fromEntity(Subscription plan) {
            return new Response(
                    plan.getId(),
                    plan.getName(),
                    plan.getDetails(),
                    plan.getPrice(),
                    plan.getDurationMonths(),
                    plan.isActive(),
                    plan.getServiceType(),
                    plan.getApprovalStatus(),
                    plan.getProposedByUserId(),
                    plan.getAddressId()
            );
        }
    }
}
