package com.smarth.solutions.core.api.dto;

import java.time.LocalDateTime;

import com.smarth.solutions.core.api.model.entity.UserSubscription;

public class UserSubscriptionDTO {
    
   public record ActivateRequest(
            Long planId
    ) {}

    public record Response(
            Long subscriptionId,     
            Long userId,
            String status,           
            LocalDateTime startDate, 
            LocalDateTime currentPeriodStart,
            LocalDateTime currentPeriodEnd,
            boolean cancelAtPeriodEnd,
            
            Long planId,
            String planName
    ) {
        public static Response fromEntity(UserSubscription sub) {
            return new Response(
                    sub.getId(),
                    sub.getUserId(),
                    sub.getStatus().name(),
                    sub.getStartDate(),
                    sub.getCurrentPeriodStart(),
                    sub.getCurrentPeriodEnd(),
                    sub.isCancelAtPeriodEnd(),
                    sub.getSubscription().getId(),
                    sub.getSubscription().getName()
            );
        }
    }
}
