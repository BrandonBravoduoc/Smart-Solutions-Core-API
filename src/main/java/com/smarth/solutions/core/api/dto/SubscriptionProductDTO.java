package com.smarth.solutions.core.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionProductDTO {
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long price;

    @NotNull
    private Integer durationMonths;

    private Integer trialDays;


}
