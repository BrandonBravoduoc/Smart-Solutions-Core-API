package com.smarth.solutions.core.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSubscriptionDTO(

    @Schema(description = "ID único de la suscripción del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id,

    @NotBlank(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID único del usuario", example = "1")
    String userId,

    @NotNull(message = "El ID del producto de suscripción es obligatorio")
    @Schema(description = "ID único del producto de suscripción", example = "1")
    Long productId,

    @NotBlank(message = "El estado de la suscripción es obligatorio")
    @Schema(description = "Estado de la suscripción", example = "active", allowableValues = {"active", "expired", "cancelled"})
    String status,

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Fecha de inicio de la suscripción", example = "2023-01-01T00:00:00")
    LocalDateTime startAt,

    @NotNull(message = "La fecha de finalización es obligatoria")
    @Schema(description = "Fecha de finalización del período actual", example = "2023-01-31T23:59:59")
    LocalDateTime currentPeriodEnd

) {}
