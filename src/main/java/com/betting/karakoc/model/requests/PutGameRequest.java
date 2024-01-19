package com.betting.karakoc.model.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PutGameRequest {


    @NotNull
    @NotBlank
    @NotEmpty
    public List<Integer> scores;


}
