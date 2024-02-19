package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteGameRequest {
    @NotBlank
   private  String betroundId;
    @NotBlank private String gameId;
    @NotBlank private String adminToken;

}
