package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetCreatedBetRoundsRequest {
    @NotBlank
    private String adminToken;
}
