package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GetPlannedBetRoundsRequest {
    @NotBlank private String userToken;
}
