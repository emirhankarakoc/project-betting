package com.betting.karakoc.model.real;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private Long betroundId;
    private String firstTeam;
    private String secondTeam;
    int scoreFirstTeam;
    int scoreSecondTeam;



}
