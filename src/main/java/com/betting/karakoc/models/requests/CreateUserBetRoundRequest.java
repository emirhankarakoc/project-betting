package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class CreateUserBetRoundRequest {

    @NotBlank
    private Long betRoundEntityId;
    @NotBlank
    private String userToken;

}
