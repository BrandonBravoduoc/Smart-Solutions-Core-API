package com.smarth.solutions.core.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Plan o producto de suscripción")
public class SubscriptionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @Schema(description = "Precio en CLP, sin decimales")
    private Long price;

    @Column(nullable = false)
    @Schema(description = "Duración en meses")
    private Integer durationMonths;

    @Column
    @Schema(description = "Días de prueba gratuitos (0 si no hay prueba)")
    private Integer trialDays;


}
