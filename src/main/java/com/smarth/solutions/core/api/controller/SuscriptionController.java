package com.smarth.solutions.core.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smarth.solutions.core.api.dto.SuscriptionDTO;
import com.smarth.solutions.core.api.service.SuscriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/suscriptions")
public class SuscriptionController {

    private final SuscriptionService service;

    public SuscriptionController(SuscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<SuscriptionDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscriptionDTO> getById(@PathVariable Long id) {
        SuscriptionDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<SuscriptionDTO> create(@Valid @RequestBody SuscriptionDTO dto) {
        SuscriptionDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getIdSuscription()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuscriptionDTO> update(@PathVariable Long id, @Valid @RequestBody SuscriptionDTO dto) {
        SuscriptionDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
