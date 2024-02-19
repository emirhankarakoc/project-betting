package com.betting.karakoc.models.dtos;


import com.betting.karakoc.models.enums.BetStatus;
import com.betting.karakoc.models.real.GameEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BetRoundEntityDTO {
    private String id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
    private String title;
    private LocalDate playDateTime;
    private BetStatus status;
    private List<GameEntity> games = new ArrayList<>();
}
