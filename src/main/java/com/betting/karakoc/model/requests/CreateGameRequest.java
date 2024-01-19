package com.betting.karakoc.model.requests;


import com.betting.karakoc.model.enums.GameType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private List<String> teams;
    @NotNull@NotBlank@NotEmpty
    private GameType gameType;


}
