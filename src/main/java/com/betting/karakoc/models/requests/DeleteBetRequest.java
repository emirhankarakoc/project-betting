package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteBetRequest {
    @NotBlank private String betroundId;
    @NotBlank private String userbetroundId;
    @NotBlank private String betId;
    @NotBlank private String adminToken;
}
