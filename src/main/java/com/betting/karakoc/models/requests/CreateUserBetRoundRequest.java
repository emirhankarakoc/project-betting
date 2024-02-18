package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class CreateUserBetRoundRequest {

    @NotBlank
    private Long betRoundEntityId;

}
