package com.smarth.solutions.core.api.controller;

import com.smarth.solutions.core.api.dto.SubscriptionDTO;
import com.smarth.solutions.core.api.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO.Response>> getAllActivePlans() {
        return ResponseEntity.ok(subscriptionService.getAllActivePlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO.Response> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getPlanResponseById(id));
    }


    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<List<SubscriptionDTO.Response>> getAllPlansForAdmin() {
        return ResponseEntity.ok(subscriptionService.getAllPlansForAdmin());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionDTO.Response> createPlan(@RequestBody SubscriptionDTO.Request requestDto) {
        SubscriptionDTO.Response createdPlan = subscriptionService.createPlan(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<SubscriptionDTO.Response> updatePlan(
            @PathVariable Long id, 
            @RequestBody SubscriptionDTO.Request requestDto) {
        return ResponseEntity.ok(subscriptionService.updatePlan(id, requestDto));
    }
}
