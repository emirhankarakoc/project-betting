package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class DeleteBetRoundRequest {

    @NotBlank
    private Long betroundId;
    @NotBlank
    private String adminToken;
}
