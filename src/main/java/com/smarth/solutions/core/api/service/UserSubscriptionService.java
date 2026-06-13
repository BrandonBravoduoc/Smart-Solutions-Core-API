package com.smarth.solutions.core.api.service;

import com.smarth.solutions.core.api.dto.UserSubscriptionDTO;
import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.entity.UserSubscription;
import com.smarth.solutions.core.api.model.enums.HistoryAction;
import com.smarth.solutions.core.api.model.enums.SubscriptionStatus;
import com.smarth.solutions.core.api.repository.UserSubscriptionRepository;
import com.smarth.solutions.core.api.util.Validations;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserSubscriptionService {

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private SubscriptionHistoryService historyService;
    
    @Autowired
    private Validations validations;

    @Cacheable(value = "user_subscription_dto", key = "#userId", unless = "#result == null || #result.isEmpty()")
    public Optional<UserSubscriptionDTO.Response> getSubscriptionDtoByUserId(Long userId) {
        validations.validateRequiredId(userId, "userId");
        return userSubscriptionRepository.findByUserId(userId)
                .map(UserSubscriptionDTO.Response::fromEntity); 
    }

    @Transactional
    @CacheEvict(value = "user_subscription_dto", key = "#userId")
    public UserSubscriptionDTO.Response activateOrRenewSubscription(Long userId, UserSubscriptionDTO.ActivateRequest request) {
        validations.validateRequiredId(userId, "userId");
        validations.validateRequiredId(request.planId(), "planId");

        Subscription plan = subscriptionService.getPlanEntityById(request.planId());
        validations.validatePlanIsActiveForPurchase(plan);

        Optional<UserSubscription> existingSubOpt = userSubscriptionRepository.findByUserId(userId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime periodEnd = now.plusMonths(plan.getDurationMonths());

        UserSubscription sub;
        HistoryAction action;

        if (existingSubOpt.isPresent()) {
            sub = existingSubOpt.get();
            action = Objects.equals(sub.getSubscription().getId(), request.planId()) ? HistoryAction.RENEWAL : HistoryAction.PLAN_CHANGE;
            
            
            sub.setSubscription(plan);
            sub.setStatus(SubscriptionStatus.ACTIVE);
            sub.setCurrentPeriodStart(now);
            sub.setCurrentPeriodEnd(periodEnd);
            sub.setCancelAtPeriodEnd(false); 
        } else {
            sub = new UserSubscription();
            sub.setUserId(userId);
            sub.setSubscription(plan);
            sub.setStatus(SubscriptionStatus.ACTIVE);
            sub.setStartDate(now);
            sub.setCurrentPeriodStart(now);
            sub.setCurrentPeriodEnd(periodEnd);
            sub.setCancelAtPeriodEnd(false);
            action = HistoryAction.CREATION;
        }

        UserSubscription savedSub = userSubscriptionRepository.save(sub);
        historyService.recordEvent(userId, plan, now, periodEnd, action);

        return UserSubscriptionDTO.Response.fromEntity(savedSub); 
    }

    @Transactional
    @CacheEvict(value = "user_subscription_dto", key = "#userId")
    public UserSubscriptionDTO.Response cancelRenewal(Long userId) {
        validations.validateRequiredId(userId, "userId");

        UserSubscription sub = userSubscriptionRepository.findByUserId(userId).orElse(null);
        validations.validateSubscriptionForCancellation(sub); 

        sub.setCancelAtPeriodEnd(true);
        sub.setStatus(SubscriptionStatus.CANCELED); 
        
        UserSubscription savedSub = userSubscriptionRepository.save(sub);

        historyService.recordEvent(userId, sub.getSubscription(), sub.getCurrentPeriodStart(), sub.getCurrentPeriodEnd(), HistoryAction.CANCELLATION);

        return UserSubscriptionDTO.Response.fromEntity(savedSub);
    }
}
