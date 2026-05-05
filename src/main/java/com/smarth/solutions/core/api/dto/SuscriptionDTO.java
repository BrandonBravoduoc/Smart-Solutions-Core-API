package com.smarth.solutions.core.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscriptionDTO {

    private Long idSuscription;

    @NotBlank
    @Size(max = 8)
    private String nameSuscription;

    @NotBlank
    private String descriptionSuscription;

    @Positive
    private double priceSuscription;

    @Positive
    private int durationMonthSuscription;

    @NotBlank
    private String dateSuscription;

    @NotBlank
    private String dateExpirationSuscription;

    @NotBlank
    private String statusSuscription;

    @NotBlank
    private String trialDays;

    @NotBlank
    private String idOwner;
}
