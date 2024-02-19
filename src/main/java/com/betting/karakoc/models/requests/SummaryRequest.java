package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SummaryRequest {
    @NotBlank
    private Long userBetRoundId;
    @NotBlank
    private String adminToken;
}
