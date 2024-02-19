package com.betting.karakoc.models.requests;

import com.betting.karakoc.models.enums.GameType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateGameRequest {

    @NotNull
    private String betroundId;

    @NotNull
    @Min(1)
    private Integer teamsSize;

    @NotEmpty
    @Size(min = 1)
    private List<String> teams;

    @NotNull
    private GameType gameType;

    @NotBlank
    @Size(min = 1)
    private String adminToken;

    @AssertTrue
    private boolean listTeamSizeEqualsIntegerTeamsSize() {
        return teams != null && teamsSize.equals(teams.size());
    }

}
