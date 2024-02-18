package com.betting.karakoc.models.requests;


import com.betting.karakoc.models.enums.GameType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {

    @NotEmpty
    private List<String> teams;
    @NotEmpty
    private GameType gameType;


}
