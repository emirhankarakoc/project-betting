package com.betting.karakoc.models.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PutGameRequest {

    @NotEmpty
    private List<Integer> scores;
    @NotBlank
    private String adminToken;
    @NotBlank
    private String betRoundId;
    @NotBlank
    private String gameId;


}
