package com.betting.karakoc.models.dtos;

import com.betting.karakoc.models.real.UserBetEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserBetRoundEntityDTO {
    private String id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
    private String userToken;
    private String betRoundEntityId;
    private List<UserBetEntity> userBetList;
    private int correctGuessedMatchCount;
}
