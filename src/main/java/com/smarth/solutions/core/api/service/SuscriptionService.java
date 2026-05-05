package com.smarth.solutions.core.api.service;

import java.util.List;

import com.smarth.solutions.core.api.dto.SuscriptionDTO;

public interface SuscriptionService {

    List<SuscriptionDTO> getAll();

    SuscriptionDTO getById(Long id);

    SuscriptionDTO create(SuscriptionDTO dto);

    SuscriptionDTO update(Long id, SuscriptionDTO dto);

    void delete(Long id);
}
