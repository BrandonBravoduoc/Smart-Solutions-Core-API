package com.smarth.solutions.core.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smarth.solutions.core.api.dto.UserSubscriptionDTO;
import com.smarth.solutions.core.api.service.UserSubscriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/subscriptions")
public class UserSubscriptionController {

    private final UserSubscriptionService service;

    public UserSubscriptionController(UserSubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserSubscriptionDTO> listByUser(@PathVariable String userId) {
        return service.findByUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSubscriptionDTO> getById(@PathVariable String userId, @PathVariable Long id) {
        UserSubscriptionDTO dto = service.findById(id);
        if (!dto.getUserId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserSubscriptionDTO> create(@PathVariable String userId, @Valid @RequestBody UserSubscriptionDTO dto) {
        dto.setUserId(userId);
        UserSubscriptionDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserSubscriptionDTO> update(@PathVariable String userId, @PathVariable Long id, @RequestBody UserSubscriptionDTO dto) {
        UserSubscriptionDTO existing = service.findById(id);
        if (!existing.getUserId().equals(userId)) return ResponseEntity.notFound().build();
        UserSubscriptionDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String userId, @PathVariable Long id) {
        UserSubscriptionDTO existing = service.findById(id);
        if (!existing.getUserId().equals(userId)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
