package com.smarth.solutions.core.api.util;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.entity.UserSubscription;
import com.smarth.solutions.core.api.model.enums.SubscriptionStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Validations {

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
        if (plan.getPrice() == null || plan.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del plan no puede ser nulo ni un valor negativo.");
        }
        if (plan.getDurationMonths() == null || plan.getDurationMonths() <= 0) {
            throw new IllegalArgumentException("La duración del plan debe ser de al menos 1 mes.");
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