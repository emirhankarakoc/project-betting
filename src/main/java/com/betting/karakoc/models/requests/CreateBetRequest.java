package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateBetRequest {
    @NotBlank private Long betRoundId;
    @NotBlank private Long userBetRoundId;
    @NotBlank private Long gameId;
    @NotBlank private Long betTeamId;
    @NotBlank private String userToken;
}
