package com.smarth.solutions.core.api.controller;

import com.smarth.solutions.core.api.dto.UserSubscriptionDTO;
import com.smarth.solutions.core.api.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions/{userId}/subscription")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or authentication.name == #userId.toString()")
    public ResponseEntity<UserSubscriptionDTO.Response> getSubscriptionByUserId(@PathVariable Long userId) {
        return userSubscriptionService.getSubscriptionDtoByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMINISTRADOR') or authentication.name == #userId.toString()")
    public ResponseEntity<UserSubscriptionDTO.Response> activateOrRenew(
            @PathVariable Long userId,
            @RequestBody UserSubscriptionDTO.ActivateRequest request) {
        return ResponseEntity.ok(userSubscriptionService.activateOrRenewSubscription(userId, request));
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('ADMINISTRADOR') or authentication.name == #userId.toString()")
    public ResponseEntity<UserSubscriptionDTO.Response> cancelRenewal(@PathVariable Long userId) {
        return ResponseEntity.ok(userSubscriptionService.cancelRenewal(userId));
    }
}
