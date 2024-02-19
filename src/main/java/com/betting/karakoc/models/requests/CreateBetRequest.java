package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBetRequest {
    @NotBlank private String betRoundId;
    @NotBlank private String userBetRoundId;
    @NotBlank private String gameId;
    @NotBlank private int betTeamId;
    @NotBlank private String userToken;
}
