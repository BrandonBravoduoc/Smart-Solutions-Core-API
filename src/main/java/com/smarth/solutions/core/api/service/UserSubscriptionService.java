package com.smarth.solutions.core.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smarth.solutions.core.api.dto.UserSubscriptionDTO;
import com.smarth.solutions.core.api.model.SubscriptionProduct;
import com.smarth.solutions.core.api.model.UserSubscription;
import com.smarth.solutions.core.api.repository.SubscriptionProductRepository;
import com.smarth.solutions.core.api.repository.UserSubscriptionRepository;
import com.smarth.solutions.core.api.util.Validation;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserSubscriptionService {

    private final UserSubscriptionRepository repository;
    private final SubscriptionProductRepository productRepository;
    private final Validation validation;

    public UserSubscriptionService(UserSubscriptionRepository repository, 
                                   SubscriptionProductRepository productRepository, 
                                   Validation validation) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.validation = validation;
    }

    public List<UserSubscriptionDTO> findByUser(String userId) {
        return repository.findByUserId(userId)
        .stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }

    public UserSubscriptionDTO findById(Long id) {
        UserSubscription s = validation.requireUserSubscription(id);
        return toDto(s);
    }

    public UserSubscriptionDTO create(UserSubscriptionDTO dto) {
        
        SubscriptionProduct product = validation.requireProduct(dto.getProductId());
        UserSubscription s = new UserSubscription();
        
        s.setUserId(dto.getUserId());
        s.setProduct(product);
        s.setStatus(dto.getStatus() == null ? "PENDING" : dto.getStatus());
        s.setStartAt(dto.getStartAt() == null ? LocalDateTime.now() : dto.getStartAt());
        s.setCurrentPeriodEnd(dto.getCurrentPeriodEnd());
        
        UserSubscription saved = repository.save(s);
        
        return toDto(saved);
    }

    public UserSubscriptionDTO update(Long id, UserSubscriptionDTO dto) {

        UserSubscription existing = validation.requireUserSubscription(id);
        
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        
        if (dto.getCurrentPeriodEnd() != null) existing.setCurrentPeriodEnd(dto.getCurrentPeriodEnd());
        
        UserSubscription saved = repository.save(existing);
        return toDto(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private UserSubscriptionDTO toDto(UserSubscription s) {
        return new UserSubscriptionDTO(s.getId(), 
        s.getUserId(), 
        s.getProduct().getId(), 
        s.getStatus(), 
        s.getStartAt(), 
        s.getCurrentPeriodEnd());
    }
}
