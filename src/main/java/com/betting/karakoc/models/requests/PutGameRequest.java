package com.betting.karakoc.models.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PutGameRequest {

    @NotEmpty
    private List<Integer> scores;
    @NotBlank
    private String adminToken;
    @NotBlank
    private Long betRoundId;
    @NotBlank
    private Long gameId;


}
