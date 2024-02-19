package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteBetRoundRequest {

    @NotBlank
    private Long betroundId;
    @NotBlank
    private String adminToken;
}
