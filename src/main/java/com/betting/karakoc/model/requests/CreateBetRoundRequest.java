package com.betting.karakoc.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBetRoundRequest {

    @NotNull@NotBlank@NotEmpty
    private String title;
    @NotNull@NotBlank@NotEmpty

    private LocalDateTime playDateTime;

}
