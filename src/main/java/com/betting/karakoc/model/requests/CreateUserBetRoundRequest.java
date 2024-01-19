package com.betting.karakoc.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class CreateUserBetRoundRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private Long betRoundEntityId;

}
