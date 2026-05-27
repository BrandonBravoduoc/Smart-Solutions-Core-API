package com.smarth.solutions.core.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

public record SubscriptionProductDTO(

    @Schema(description = "ID único del producto de suscripción", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id,

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Schema(description = "Nombre comercial del plan", example = "Plan Premium")
    String name,

    @NotBlank(message = "La descripción no puede estar vacía")
    @Schema(description = "Descripción detallada de los beneficios del plan", example = "Acceso ilimitado a todas las funciones premium por 1 mes.")
    String description,

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a cero")
    @Schema(description = "Precio del producto en CLP", example = "9990")
    Long price,

    @NotNull(message = "La duración en meses es obligatoria")
    @Schema(description = "Duración del plan expresada en meses", example = "1")
    Integer durationInMonths

) {}