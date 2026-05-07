package com.smarth.solutions.core.api.util;

import org.springframework.stereotype.Component;

import com.smarth.solutions.core.api.model.Suscription;
import com.smarth.solutions.core.api.repository.SuscriptionRepository;

@Component
public class validation {

    private SuscriptionRepository repository;

  

    public Suscription requireSuscription(Long id) {
            return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Suscripcion no encontrada: " + id));

    }

}