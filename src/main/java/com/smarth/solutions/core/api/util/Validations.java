package com.smarth.solutions.core.api.util;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.entity.UserSubscription;
import com.smarth.solutions.core.api.model.enums.ApprovalStatus;
import com.smarth.solutions.core.api.model.enums.ServiceType;
import com.smarth.solutions.core.api.model.enums.SubscriptionStatus;
import com.smarth.solutions.core.api.repository.SubscriptionRepository;
import com.smarth.solutions.core.api.repository.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Validations {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    public void validateRequiredId(Long id, String idName) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El campo '" + idName + "' es obligatorio y debe ser un ID válido mayor a cero.");
        }
    }


    public void validatePlanDetails(Subscription plan) {
        if (plan == null) {
            throw new IllegalArgumentException("Los datos del plan de suscripción no pueden ser nulos.");
        }
        if (plan.getName() == null || plan.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plan es un campo obligatorio y no puede estar vacío.");
        }
        if (plan.getName().trim().length() < 10 || plan.getName().trim().length() > 100) {
            throw new IllegalArgumentException("El nombre del plan debe tener entre 10 y 100 caracteres.");
        }
        if (plan.getDetails() == null || plan.getDetails().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del plan es un campo obligatorio y no puede estar vacía.");
        }
        if (plan.getDetails().trim().length() > 255) {
            throw new IllegalArgumentException("La descripción del plan no puede superar los 255 caracteres.");
        }
        if (plan.getPrice() == null || plan.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del plan no puede ser nulo ni un valor negativo.");
        }
        if (plan.getDurationMonths() == null || plan.getDurationMonths() <= 0) {
            throw new IllegalArgumentException("La duración del plan debe ser de al menos 1 mes.");
        }
        if (plan.getServiceType() == null) {
            throw new IllegalArgumentException("Debe indicar si el servicio es virtual, presencial o ambas.");
        }
        if (plan.getServiceType() == ServiceType.VIRTUAL && plan.getAddressId() != null) {
            throw new IllegalArgumentException("Un servicio virtual no puede tener una sucursal asociada.");
        }
        if (plan.getServiceType() != ServiceType.VIRTUAL && plan.getAddressId() == null) {
            throw new IllegalArgumentException("Debe indicar la sucursal para un servicio presencial o mixto.");
        }
    }

    public void assertNameNotDuplicated(String name, Long excludeId) {
        boolean duplicated = excludeId == null
            ? subscriptionRepository.existsByNameIgnoreCase(name.trim())
            : subscriptionRepository.existsByNameIgnoreCaseAndIdNot(name.trim(), excludeId);
        if (duplicated) {
            throw new IllegalArgumentException("Ya existe un plan de suscripción con el nombre '" + name.trim() + "'.");
        }
    }


    public void assertPlanDeletable(Long planId) {
        if (userSubscriptionRepository.existsBySubscription_IdAndStatus(planId, SubscriptionStatus.ACTIVE)) {
            throw new IllegalStateException(
                "No se puede eliminar este plan porque tiene al menos un usuario con una suscripción activa. Solo puedes modificarlo o desactivarlo.");
        }
    }

    public void assertPlanDeactivatable(Long planId) {
        if (userSubscriptionRepository.existsBySubscription_IdAndStatus(planId, SubscriptionStatus.ACTIVE)) {
            throw new IllegalStateException(
                "No se puede desactivar este plan porque tiene al menos un usuario con una suscripción activa.");
        }
    }

    public void assertPlanApprovedForActivation(Subscription plan) {
        if (plan.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new IllegalStateException(
                "No puedes activar este plan hasta que un administrador apruebe tu propuesta (estado actual: " + plan.getApprovalStatus() + ").");
        }
    }

    public void assertPendingApproval(Subscription plan) {
        if (plan.getApprovalStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException(
                "Esta propuesta ya fue procesada (estado actual: " + plan.getApprovalStatus() + ").");
        }
    }

    public void validatePlanIsActiveForPurchase(Subscription plan) {
        if (!plan.isActive()) {
            throw new IllegalStateException("El plan '" + plan.getName() + "' ya no está disponible para nuevas suscripciones (Descontinuado).");
        }
    }

    public void validateSubscriptionForCancellation(UserSubscription sub) {
        if (sub == null) {
            throw new IllegalArgumentException("No se encontró ninguna suscripción activa registrada para este usuario.");
        }
  
        if (sub.isCancelAtPeriodEnd()) {
            throw new IllegalStateException("La renovación de esta suscripción ya fue cancelada previamente.");
        }
        if (sub.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("No se puede cancelar una suscripción que se encuentra en estado: " + sub.getStatus());
        }
    }
}