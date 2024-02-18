package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserBetRoundRequest {

    @NotBlank private Long betroundId; @NotBlank private Long userbetroundId;
    @NotBlank private String adminToken;
}
