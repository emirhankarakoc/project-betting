package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class EndBetRoundRequest {
    @NotBlank
    private String betroundId;
    @NotBlank
    private String adminToken;
}
