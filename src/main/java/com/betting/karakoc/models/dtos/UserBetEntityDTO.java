package com.betting.karakoc.models.dtos;

import lombok.Data;

@Data
public class UserBetEntityDTO {
    private String id;
    private String userBetRoundId;
    private String gameEntityId;
    private int betTeamId;
    private Boolean isGuessCorrect;
    private String teams;
}
