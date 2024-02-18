package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EndBetRoundRequest {
    @NotBlank
    private Long betroundId;
    @NotBlank
    private String adminToken;
}
