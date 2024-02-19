package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class CreateUserBetRoundRequest {

    @NotBlank
    private String betRoundEntityId;
    @NotBlank
    private String userToken;

}
