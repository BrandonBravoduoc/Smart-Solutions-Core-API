package com.smarth.solutions.core.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.smarth.solutions.core.api.dto.SuscriptionDTO;
import com.smarth.solutions.core.api.model.Suscription;
import com.smarth.solutions.core.api.repository.SuscriptionRepository;
import com.smarth.solutions.core.api.util.validation;

@Service
@Transactional
public class SuscriptionService {

    private validation validation;
    private SuscriptionRepository repository;

    public SuscriptionService(SuscriptionRepository repository) {
        this.repository = repository;
    }

    public List<SuscriptionDTO> findAll() {
        return repository.findAll().stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }

    public SuscriptionDTO findById(Long id) {
        Suscription s = validation.requireSuscription(id);
        return toDto(s);
    }

    public SuscriptionDTO create(SuscriptionDTO dto) {
        Suscription s = toEntity(dto);
        Suscription saved = repository.save(s);
        return toDto(saved);
    }

    public SuscriptionDTO update(Long id, SuscriptionDTO dto) {
        Suscription existing = validation.requireSuscription(id);

        existing.setNameSuscription(dto.getNameSuscription());
        existing.setDescriptionSuscription(dto.getDescriptionSuscription());
        existing.setPriceSuscription(dto.getPriceSuscription());
        existing.setDurationMonthSuscription(dto.getDurationMonthSuscription());
        existing.setDateSuscription(dto.getDateSuscription());
        existing.setDateExpirationSuscription(dto.getDateExpirationSuscription());
        existing.setStatusSuscription(dto.getStatusSuscription());
        existing.setTrialDays(dto.getTrialDays());
        existing.setIdOwner(dto.getIdOwner());

        Suscription saved = repository.save(existing);
        return toDto(saved);
    }

        repository.deleteById(id);
    }

    private SuscriptionDTO toDto(Suscription s) {
        SuscriptionDTO dto = new SuscriptionDTO();
        dto.setIdSuscription(s.getIdSuscription());
        dto.setNameSuscription(s.getNameSuscription());
        dto.setDescriptionSuscription(s.getDescriptionSuscription());
        dto.setPriceSuscription(s.getPriceSuscription());
        dto.setDurationMonthSuscription(s.getDurationMonthSuscription());
        dto.setDateSuscription(s.getDateSuscription());
        dto.setDateExpirationSuscription(s.getDateExpirationSuscription());
        dto.setStatusSuscription(s.getStatusSuscription());
        dto.setTrialDays(s.getTrialDays());
        dto.setIdOwner(s.getIdOwner());
        return dto;
    }

    private Suscription toEntity(SuscriptionDTO dto) {
        Suscription s = new Suscription();
        s.setNameSuscription(dto.getNameSuscription());
        s.setDescriptionSuscription(dto.getDescriptionSuscription());
        s.setPriceSuscription(dto.getPriceSuscription());
        s.setDurationMonthSuscription(dto.getDurationMonthSuscription());
        s.setDateSuscription(dto.getDateSuscription());
        s.setDateExpirationSuscription(dto.getDateExpirationSuscription());
        s.setStatusSuscription(dto.getStatusSuscription());
        s.setTrialDays(dto.getTrialDays());
        s.setIdOwner(dto.getIdOwner());
        return s;
    }
}
