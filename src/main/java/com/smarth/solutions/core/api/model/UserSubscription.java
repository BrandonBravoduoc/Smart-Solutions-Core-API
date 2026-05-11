package com.smarth.solutions.core.api.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_subscription")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Suscripción activa de un usuario")
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private SubscriptionProduct product;

    @Column(nullable = false)
    @Schema(description = "Estado de la suscripción: ACTIVE, PENDING, CANCELLED, EXPIRED, TRIAL")
    private String status; 

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime currentPeriodEnd;

}
