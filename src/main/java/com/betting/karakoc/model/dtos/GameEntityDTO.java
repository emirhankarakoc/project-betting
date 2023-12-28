package com.betting.karakoc.model.dtos;


import com.betting.karakoc.model.real.GameEntity;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GameEntityDTO {

    private Long id;
    private Long betroundId;
    private String firstTeam;
    private String secondTeam;
    int scoreFirstTeam;
    int scoreSecondTeam;
}
