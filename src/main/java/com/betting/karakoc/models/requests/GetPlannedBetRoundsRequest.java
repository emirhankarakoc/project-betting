package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GetPlannedBetRoundsRequest {
    @NotBlank int creatorCode;
}
