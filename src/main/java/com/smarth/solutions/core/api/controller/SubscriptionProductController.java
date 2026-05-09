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

import com.smarth.solutions.core.api.dto.SubscriptionProductDTO;
import com.smarth.solutions.core.api.service.SubscriptionProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/subscription-products")
public class SubscriptionProductController {

    private final SubscriptionProductService service;

    public SubscriptionProductController(SubscriptionProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<SubscriptionProductDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionProductDTO> create(@Valid @RequestBody SubscriptionProductDTO dto) {
        SubscriptionProductDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionProductDTO> update(@PathVariable Long id, @Valid @RequestBody SubscriptionProductDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
