package com.smarth.solutions.core.api.controller;

import com.smarth.solutions.core.api.dto.SubscriptionDTO;
import com.smarth.solutions.core.api.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasRole('ADMINISTRADOR')") 
    public ResponseEntity<List<SubscriptionDTO.Response>> getAllPlansForAdmin() {
        return ResponseEntity.ok(subscriptionService.getAllPlansForAdmin());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<SubscriptionDTO.Response> createPlan(@RequestBody SubscriptionDTO.Request requestDto) {
        SubscriptionDTO.Response createdPlan = subscriptionService.createPlan(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
    }


    // El dueño (quien propuso el plan) también puede editar el suyo, además del admin.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @subscriptionService.isOwnPlan(#id, authentication.name)")
    public ResponseEntity<SubscriptionDTO.Response> updatePlan(
            @PathVariable Long id,
            @RequestBody SubscriptionDTO.Request requestDto) {
        return ResponseEntity.ok(subscriptionService.updatePlan(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        subscriptionService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Un cliente propone "hostear" su propio servicio de suscripción (virtual,
     * presencial o ambas). Queda pendiente de aprobación del administrador.
     */
    @PostMapping("/propose")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<SubscriptionDTO.Response> proposePlan(
            @RequestBody SubscriptionDTO.Request requestDto,
            Authentication authentication) {
        Long proposedByUserId = Long.valueOf(authentication.getName());
        SubscriptionDTO.Response proposedPlan = subscriptionService.proposePlan(requestDto, proposedByUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(proposedPlan);
    }

    @GetMapping("/admin/pending")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<SubscriptionDTO.Response>> getPendingPlans() {
        return ResponseEntity.ok(subscriptionService.getPendingPlans());
    }

    /**
     * Planes que el cliente logueado propuso (es dueño), en cualquier estado,
     * para que los gestione desde su perfil.
     */
    @GetMapping("/mine")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<SubscriptionDTO.Response>> getMyPlans(Authentication authentication) {
        Long currentUserId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(subscriptionService.getMyPlans(currentUserId));
    }

    @PostMapping("/admin/{id}/approve")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<SubscriptionDTO.Response> approvePlan(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.approvePlan(id));
    }

    @PostMapping("/admin/{id}/reject")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<SubscriptionDTO.Response> rejectPlan(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.rejectPlan(id));
    }
}