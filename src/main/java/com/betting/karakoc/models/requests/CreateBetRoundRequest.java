package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@Builder

public class CreateBetRoundRequest {

    @NotBlank
    @Size(min = 3, max = 64, message = "Betround title length must be between 3-64")
    private String title;

    @NotNull
    private LocalDate lastBetTime;

    @NotBlank
    private String adminToken;

    @AssertTrue(message = "Last Bet Time must not be in the past.")
    private boolean isPlaydatePast() {
        return lastBetTime.isAfter(LocalDate.now());
    }
}
