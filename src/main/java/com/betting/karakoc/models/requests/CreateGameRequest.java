package com.betting.karakoc.models.requests;


import com.betting.karakoc.models.enums.GameType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {

    @NotNull
    private Long betroundId;

    @NotNull
    @Min(1)
    private Integer teamsSize;

    @NotEmpty
    @Size(min = 1)
    private List<String> teams;

    @NotEmpty
    private GameType gameType;

    @NotBlank
    @Size(min = 1)
    private String adminToken;


    @AssertTrue
    private boolean listTeamSizeEqualsIntegerTeamsSize() {
        return teams != null && teamsSize.equals(teams.size());
    }


}
