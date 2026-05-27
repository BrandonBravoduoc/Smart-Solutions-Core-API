package com.smarth.solutions.core.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smarth.solutions.core.api.dto.SubscriptionProductDTO;
import com.smarth.solutions.core.api.model.SubscriptionProduct;
import com.smarth.solutions.core.api.repository.SubscriptionProductRepository;
import com.smarth.solutions.core.api.util.Validation;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubscriptionProductService {

    private final SubscriptionProductRepository repository;
    private final Validation validation;

    public SubscriptionProductService(SubscriptionProductRepository repository, Validation validation) {
        this.repository = repository;
        this.validation = validation;
    }

    public List<SubscriptionProductDTO> findAll() {
        return repository
        .findAll()
        .stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }

    public SubscriptionProductDTO findById(Long id) {
        SubscriptionProduct p = validation.requireProduct(id);
        return toDto(p);
    }

    public SubscriptionProductDTO create(SubscriptionProductDTO dto) {
        SubscriptionProduct p = toEntity(dto);
        SubscriptionProduct saved = repository.save(p);
        return toDto(saved);
    }

    public SubscriptionProductDTO update(Long id, SubscriptionProductDTO dto) {
        SubscriptionProduct existing = validation.requireProduct(id);
        
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setPrice(dto.price());
        existing.setDurationMonths(dto.durationInMonths());
        
        SubscriptionProduct saved = repository.save(existing);
        return toDto(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private SubscriptionProductDTO toDto(SubscriptionProduct p) {
        return new SubscriptionProductDTO(
            p.getId(), 
            p.getName(), 
            p.getDescription(), 
            p.getPrice(), 
            p.getDurationMonths()
        );
    }

    private SubscriptionProduct toEntity(SubscriptionProductDTO dto) {
        SubscriptionProduct p = new SubscriptionProduct();
        
        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setPrice(dto.price());
        p.setDurationMonths(dto.durationInMonths());
        
        return p;
    }
}