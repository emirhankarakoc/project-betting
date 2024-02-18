package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class DeleteGameRequest {
    @NotBlank
   private  Long betroundId;
    @NotBlank private Long gameId;
    @NotBlank private String adminToken;

}
