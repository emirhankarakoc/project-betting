package com.betting.karakoc.models.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllBetsByGameRequest {
    @NotBlank
    private String userbetRoundId;
    @NotBlank
    private String betroundId;
    @NotBlank
    private String adminToken;
}


