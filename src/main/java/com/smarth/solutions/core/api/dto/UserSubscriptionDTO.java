package com.smarth.solutions.core.api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionDTO {
    private Long id;

    @NotBlank
    private String userId;

    @NotNull
    private Long productId;

    private String status;

    private LocalDateTime startAt;

    private LocalDateTime currentPeriodEnd;

}
