package com.smarth.solutions.core.api.util;

import org.springframework.stereotype.Component;

import com.smarth.solutions.core.api.model.SubscriptionProduct;
import com.smarth.solutions.core.api.model.UserSubscription;
import com.smarth.solutions.core.api.repository.SubscriptionProductRepository;
import com.smarth.solutions.core.api.repository.UserSubscriptionRepository;

@Component
public class Validation {

    private final SubscriptionProductRepository productRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public Validation(SubscriptionProductRepository productRepository, UserSubscriptionRepository userSubscriptionRepository) {
        this.productRepository = productRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public SubscriptionProduct requireProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubscriptionProduct no encontrado: " + id));
    }

    public UserSubscription requireUserSubscription(Long id) {
        return userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserSubscription no encontrado: " + id));
    }

}
