package com.betting.karakoc.models.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PutGameRequest {

    @NotEmpty
    public List<Integer> scores;


}
