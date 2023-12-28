package com.betting.karakoc.model.real;

import com.betting.karakoc.model.enums.BetStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class BetRoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
    private String title;
    private LocalDateTime playDateTime;
    @Enumerated
    private BetStatus betStatus;
    @OneToMany
    private List<GameEntity> games;

}
