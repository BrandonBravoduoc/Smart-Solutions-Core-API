package com.smarth.solutions.core.api.controller;

import com.smarth.solutions.core.api.dto.UserSubscriptionDTO;
import com.smarth.solutions.core.api.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions/admin")
@RequiredArgsConstructor
public class AdminSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UserSubscriptionDTO.Response>> getAllSubscriptions() {
        return ResponseEntity.ok(userSubscriptionService.getAllSubscriptions());
    }
}