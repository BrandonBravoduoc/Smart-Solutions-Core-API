package com.smarth.solutions.core.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.smarth.solutions.core.api.dto.SubscriptionDTO;
import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.repository.SubscriptionRepository;
import com.smarth.solutions.core.api.util.Validations;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private Validations validations;



    @Cacheable(value = "active_plans_dto", key = "'all'")
    public List<SubscriptionDTO.Response> getAllActivePlans() {
        return subscriptionRepository.findByIsActiveTrue()
                .stream()
                .map(SubscriptionDTO.Response::fromEntity) 
                .toList();
    }

    public SubscriptionDTO.Response getPlanResponseById(Long id) {
        return SubscriptionDTO.Response.fromEntity(getPlanEntityById(id));
    }

    public List<SubscriptionDTO.Response> getAllPlansForAdmin() {
        return subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionDTO.Response::fromEntity)
                .toList();
    }

    protected Subscription getPlanEntityById(Long id) {
        validations.validateRequiredId(id, "planId");
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan de suscripción no encontrado con ID: " + id));
    }



    @Transactional
    @CacheEvict(value = "active_plans_dto", allEntries = true)
    public SubscriptionDTO.Response createPlan(SubscriptionDTO.Request requestDto) {
        Subscription plan = new Subscription();
        plan.setName(requestDto.name());
        plan.setDetails(requestDto.details());
        plan.setPrice(requestDto.price());
        plan.setDurationMonths(requestDto.durationMonths());

        validations.validatePlanDetails(plan);
        plan.setActive(true);

        Subscription savedPlan = subscriptionRepository.save(plan);
        return SubscriptionDTO.Response.fromEntity(savedPlan); 
    }

    @Transactional
    @CacheEvict(value = "active_plans_dto", allEntries = true)
    public SubscriptionDTO.Response updatePlan(Long id, SubscriptionDTO.Request requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Los datos de actualización del plan no pueden ser nulos.");
        }
        Subscription plan = getPlanEntityById(id);

        plan.setName(requestDto.name());
        plan.setDetails(requestDto.details());
        plan.setPrice(requestDto.price());
        plan.setDurationMonths(requestDto.durationMonths());
        plan.setActive(requestDto.isActive());

        validations.validatePlanDetails(plan);

        Subscription updatedPlan = subscriptionRepository.save(plan);
        return SubscriptionDTO.Response.fromEntity(updatedPlan);
    }

    @Transactional
    @CacheEvict(value = "active_plans_dto", allEntries = true)
    public void deletePlan(Long id) {
        Subscription plan = getPlanEntityById(id);
        subscriptionRepository.delete(plan);
    }
}