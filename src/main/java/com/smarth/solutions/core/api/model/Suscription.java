package com.smarth.solutions.core.api.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Suscription")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Suscripción del servicio")
public class Suscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID auto-generado", example = "1")
    private Long idSuscription;

    @Column(unique = true, nullable = false, length = 8)
    @Schema(description = "Nombre corto de la suscripción", example = "BASIC")
    @NotBlank
    @Size(max = 8)
    private String nameSuscription;

    @Column(nullable = false)
    @Schema(description = "Descripción", example = "Acceso básico a funciones")
    @NotBlank
    private String descriptionSuscription;

    @Column(nullable = false)
    @Schema(description = "Precio", example = "9.99")
    @Positive
    private double priceSuscription;

    @Column(nullable = false)
    @Schema(description = "Duración en meses", example = "12")
    @Positive
    private int durationMonthSuscription;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio (ISO)", example = "2026-04-23")
    @NotBlank
    private String dateSuscription; 

    @Column(nullable = false)
    @Schema(description = "Fecha de expiración (ISO)", example = "2027-04-23")
    @NotBlank
    private String dateExpirationSuscription;

    @Column(nullable = false)
    @Schema(description = "Estado de la suscripción", example = "ACTIVE")
    @NotBlank
    private String statusSuscription;

    @Column(nullable = false)    
    @Schema(description = "Días de prueba", example = "30")
    @NotBlank
    private String trialDays;

    @Column(nullable = false)
    @Schema(description = "ID del propietario", example = "12331")
    @NotBlank
    private String idOwner;



}
